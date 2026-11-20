package com.example.ui.auth

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.model.UserProfile
import com.example.data.repository.ExamRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val AUTH_DEBUG_TAG = "AuthDebug"

sealed interface AuthUiState {
    object Idle : AuthUiState
    object Loading : AuthUiState
    data class Success(val profile: UserProfile) : AuthUiState
    data class NeedsProfileSetup(val email: String, val displayName: String) : AuthUiState
    data class Error(val message: String) : AuthUiState
}

class AuthViewModel(private val repository: ExamRepository) : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _currentUserProfile = MutableStateFlow<UserProfile?>(null)
    val currentUserProfile: StateFlow<UserProfile?> = _currentUserProfile.asStateFlow()

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin.asStateFlow()

    private val _isSessionActive = MutableStateFlow(false)
    val isSessionActive: StateFlow<Boolean> = _isSessionActive.asStateFlow()

    private var profileJob: Job? = null
    private var activeProfileUid: String? = null

    init {
        auth.currentUser?.let { user ->
            _isSessionActive.value = true
            loadUserProfile(user.uid, user.email, user.displayName)
        }
    }

    private tailrec fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun clearError() {
        if (_uiState.value is AuthUiState.Error) {
            _uiState.value = AuthUiState.Idle
        }
    }

    fun loadUserProfile(
        uid: String,
        email: String?,
        displayName: String?,
        forceRefresh: Boolean = false
    ) {
        if (uid.isBlank()) {
            Log.e(AUTH_DEBUG_TAG, "STUDENT_PROFILE_FETCH_FAILED | op=PROFILE_FETCH | code=INVALID_UID | msg=UID is blank")
            _uiState.value = AuthUiState.Error("लॉगिन सत्र अमान्य है (UID उपलब्ध नहीं)। कृपया पुनः लॉगिन करें।")
            return
        }
        // Prevent duplicate cancellation race when both AuthStateListener & signIn callback fire for same UID
        if (!forceRefresh && activeProfileUid == uid &&
            (profileJob?.isActive == true || _uiState.value is AuthUiState.Success)
        ) {
            return
        }

        profileJob?.cancel()
        activeProfileUid = uid
        profileJob = viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                when (val fetchResult = repository.fetchStudentProfileOnce(uid)) {
                    is ExamRepository.ProfileFetchResult.Found -> {
                        val profile = fetchResult.profile
                        val verifiedRole = profile.role.ifBlank { "student" }
                        val adminAccess = verifiedRole == "admin" ||
                            email.equals("mithleshyadaw0@gmail.com", ignoreCase = true)
                        _currentUserProfile.value = profile
                        _isAdmin.value = adminAccess
                        _isSessionActive.value = true
                        _uiState.value = AuthUiState.Success(profile)
                        Log.d(
                            AUTH_DEBUG_TAG,
                            "STUDENT_DASHBOARD_OPENED | op=ROLE_VERIFIED | uid=$uid | role=$verifiedRole | path=users/$uid"
                        )
                    }

                    is ExamRepository.ProfileFetchResult.NotFound -> {
                        // Auth succeeded; only profile document is missing in Firestore -> prompt setup
                        _uiState.value = AuthUiState.NeedsProfileSetup(
                            email = email ?: "",
                            displayName = displayName ?: ""
                        )
                    }

                    is ExamRepository.ProfileFetchResult.Error -> {
                        val hindiMsg = when (fetchResult.code) {
                            "PERMISSION_DENIED" ->
                                "प्रोफ़ाइल लोड नहीं हो सकी: Firestore अनुमति अस्वीकृत (PERMISSION_DENIED)।"
                            "UNAVAILABLE", "DEADLINE_EXCEEDED" ->
                                "नेटवर्क त्रुटि: विद्यार्थी प्रोफ़ाइल लोड नहीं हो सकी। इंटरनेट जांचें और पुनः प्रयास करें।"
                            "UNAUTHENTICATED" ->
                                "लॉगिन सत्र समाप्त हो गया है। कृपया पुनः साइन-इन करें।"
                            else ->
                                "प्रोफ़ाइल लोड करने में त्रुटि (${fetchResult.code}): ${fetchResult.message}"
                        }
                        _uiState.value = AuthUiState.Error(hindiMsg)
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(
                    AUTH_DEBUG_TAG,
                    "STUDENT_PROFILE_FETCH_FAILED | op=PROFILE_FETCH | uid=$uid | path=users/$uid | msg=${e.localizedMessage}",
                    e
                )
                _uiState.value = AuthUiState.Error(
                    "प्रोफ़ाइल लोड करने में त्रुटि: ${e.localizedMessage ?: "कृपया पुनः प्रयास करें।"}"
                )
            }
        }
    }

    fun saveInitialProfile(
        name: String,
        studentId: String,
        studentClass: String,
        role: String = "student"
    ) {
        val cleanName = name.trim()
        if (cleanName.isBlank()) {
            _uiState.value = AuthUiState.Error("कृपया विद्यार्थी का नाम दर्ज करें।")
            return
        }
        val user = auth.currentUser
        if (user == null) {
            Log.e(AUTH_DEBUG_TAG, "STUDENT_PROFILE_FETCH_FAILED | op=PROFILE_SAVE | code=UNAUTHENTICATED | msg=Firebase user is null")
            _uiState.value = AuthUiState.Error("Firebase लॉगिन सक्रिय नहीं है। कृपया पहले साइन-इन करें।")
            return
        }
        val uid = user.uid
        val userEmail = user.email ?: ""
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val isTeacher = role == "admin" || userEmail.equals("mithleshyadaw0@gmail.com", ignoreCase = true)
                val finalRole = if (isTeacher) "admin" else "student"
                val profile = UserProfile(
                    userId = uid,
                    role = finalRole,
                    name = cleanName,
                    studentId = studentId.trim().ifBlank { "STU-${uid.take(6).uppercase()}" },
                    studentClass = studentClass,
                    email = userEmail
                )
                val saveResult = repository.saveUserProfile(profile)
                saveResult.fold(
                    onSuccess = { saved ->
                        _currentUserProfile.value = saved
                        _isAdmin.value = isTeacher
                        _isSessionActive.value = true
                        _uiState.value = AuthUiState.Success(saved)
                        Log.d(AUTH_DEBUG_TAG, "STUDENT_DASHBOARD_OPENED | op=PROFILE_CREATED | uid=$uid | role=$finalRole | path=users/$uid")
                    },
                    onFailure = { error ->
                        _uiState.value = AuthUiState.Error(
                            "प्रोफ़ाइल सहेजी नहीं जा सकी: ${error.localizedMessage ?: "Firestore त्रुटि"}"
                        )
                    }
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(
                    "प्रोफ़ाइल सहेजने में त्रुटि: ${e.localizedMessage ?: "कृपया पुनः प्रयास करें।"}"
                )
            }
        }
    }

    fun loginAsAdminWithPasscode(passcode: String): Boolean {
        val clean = passcode.trim().replace(" ", "_").lowercase()
        // Strict verification: only the exact teacher passcode is permitted
        val isAuthorized = clean == "mithlesh_sir" || clean == "mithlesh@sir"

        if (isAuthorized) {
            val adminProfile = UserProfile(
                userId = auth.currentUser?.uid ?: "admin_mithlesh_sir",
                role = "admin",
                name = "मिथलेश सर (व्यवस्थापक)",
                studentId = "ADMIN-01",
                studentClass = "All Competitive Exams",
                email = auth.currentUser?.email ?: "mithleshyadaw0@gmail.com"
            )
            _currentUserProfile.value = adminProfile
            _isAdmin.value = true
            _isSessionActive.value = true
            _uiState.value = AuthUiState.Success(adminProfile)
            return true
        }
        return false
    }

    fun loginAsStudentDirect(name: String, studentId: String, studentClass: String): Boolean {
        Log.d(AUTH_DEBUG_TAG, "STUDENT_LOGIN_BUTTON_CLICKED | op=DIRECT_STUDENT_LOGIN")
        val cleanName = name.trim()
        if (cleanName.isBlank()) {
            Log.w(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_FAILED | op=DIRECT_VALIDATION | code=EMPTY_NAME | msg=Student name is blank")
            _uiState.value = AuthUiState.Error("कृपया विद्यार्थी का नाम दर्ज करें।")
            return false
        }
        Log.d(AUTH_DEBUG_TAG, "LOGIN_FORM_SUBMITTED | op=DIRECT_STUDENT_LOGIN")
        val uid = auth.currentUser?.uid ?: "stu_${System.currentTimeMillis()}"
        val profile = UserProfile(
            userId = uid,
            role = "student",
            name = cleanName,
            studentId = studentId.trim().ifBlank { "MS-${(1000..9999).random()}" },
            studentClass = studentClass,
            email = auth.currentUser?.email ?: ""
        )
        _currentUserProfile.value = profile
        _isAdmin.value = false
        _isSessionActive.value = true
        _uiState.value = AuthUiState.Success(profile)
        Log.d(AUTH_DEBUG_TAG, "STUDENT_DASHBOARD_OPENED | op=DIRECT_STUDENT_LOGIN | uid=$uid | role=student")
        return true
    }

    fun signInWithGoogle(context: Context, credentialManager: CredentialManager) {
        Log.d(AUTH_DEBUG_TAG, "STUDENT_LOGIN_BUTTON_CLICKED | op=GOOGLE_SIGN_IN")
        if (_uiState.value is AuthUiState.Loading) {
            Log.w(AUTH_DEBUG_TAG, "LOGIN_FORM_SUBMITTED | op=GOOGLE_SIGN_IN | status=IGNORED_ALREADY_LOADING")
            return
        }

        val activity = context.findActivity()
        if (activity == null) {
            Log.e(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_FAILED | op=GOOGLE_SIGN_IN | code=NO_ACTIVITY | msg=Activity context unavailable")
            _uiState.value = AuthUiState.Error("लॉगिन शुरू नहीं हो सका (Activity संदर्भ उपलब्ध नहीं)। कृपया ऐप पुनः खोलें।")
            return
        }

        val clientId = try {
            context.getString(R.string.default_web_client_id)
        } catch (e: Exception) {
            Log.e(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_FAILED | op=GOOGLE_SIGN_IN | code=MISSING_WEB_CLIENT_ID | msg=${e.localizedMessage}")
            _uiState.value = AuthUiState.Error("Google Sign-In कॉन्फ़िगरेशन उपलब्ध नहीं है (default_web_client_id)।")
            return
        }

        Log.d(AUTH_DEBUG_TAG, "LOGIN_FORM_SUBMITTED | op=GOOGLE_SIGN_IN")
        val signInOption = GetSignInWithGoogleOption.Builder(serverClientId = clientId).build()
        val request = GetCredentialRequest.Builder().addCredentialOption(signInOption).build()

        _uiState.value = AuthUiState.Loading
        viewModelScope.launch {
            var authFlowCompleted = false
            try {
                val result = credentialManager.getCredential(activity, request)
                val credential = result.credential
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    Log.d(AUTH_DEBUG_TAG, "EMAIL_VALIDATION_PASSED | op=GOOGLE_CREDENTIAL_RECEIVED")
                    val authCredential = GoogleAuthProvider.getCredential(googleCredential.idToken, null)
                    Log.d(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_STARTED | op=FIREBASE_SIGN_IN_WITH_CREDENTIAL | app=${auth.app.name}")
                    val authResult = auth.signInWithCredential(authCredential).await()
                    val user = authResult.user
                    if (user != null) {
                        Log.d(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_SUCCESS | op=FIREBASE_SIGN_IN | uid=${user.uid}")
                        _isSessionActive.value = true
                        authFlowCompleted = true
                        loadUserProfile(user.uid, user.email, user.displayName, forceRefresh = true)
                    } else {
                        Log.e(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_FAILED | op=FIREBASE_SIGN_IN | code=NULL_USER | msg=Firebase returned null user")
                        _uiState.value = AuthUiState.Error("Firebase लॉगिन पूरा नहीं हो सका (उपयोगकर्ता डेटा रिक्त है)। कृपया पुनः प्रयास करें।")
                    }
                } else {
                    Log.e(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_FAILED | op=CREDENTIAL_CHECK | code=UNEXPECTED_TYPE | type=${credential.type}")
                    _uiState.value = AuthUiState.Error("अमान्य लॉगिन क्रेडेंशियल प्राप्त हुआ। कृपया Google खाते से प्रयास करें।")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: GetCredentialCancellationException) {
                Log.w(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_FAILED | op=GOOGLE_SIGN_IN | code=USER_CANCELLED | msg=${e.message}", e)
                _uiState.value = AuthUiState.Error("लॉगिन रद्द कर दिया गया। जारी रखने के लिए कृपया पुनः प्रयास करें।")
            } catch (e: NoCredentialException) {
                Log.e(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_FAILED | op=GOOGLE_SIGN_IN | code=NO_CREDENTIAL | msg=${e.message}", e)
                _uiState.value = AuthUiState.Error("इस डिवाइस में कोई Google खाता उपलब्ध नहीं मिला। कृपया Google खाता जोड़ें या सीधे नाम व रोल नंबर से लॉगिन करें।")
            } catch (e: GetCredentialException) {
                Log.e(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_FAILED | op=GOOGLE_SIGN_IN | code=${e.type} | msg=${e.message}", e)
                _uiState.value = AuthUiState.Error("Google लॉगिन विफल (${e.type}): ${e.localizedMessage ?: "कृपया पुनः प्रयास करें।"}")
            } catch (e: FirebaseAuthException) {
                Log.e(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_FAILED | op=FIREBASE_SIGN_IN | code=${e.errorCode} | msg=${e.localizedMessage}", e)
                val hindiError = when (e.errorCode) {
                    "ERROR_USER_DISABLED" -> "यह विद्यार्थी खाता निष्क्रिय (Disabled) कर दिया गया है।"
                    "ERROR_USER_NOT_FOUND" -> "यह विद्यार्थी खाता Firebase में पंजीकृत नहीं है।"
                    "ERROR_INVALID_CREDENTIAL" -> "लॉगिन क्रेडेंशियल अमान्य या समाप्त हो गया है। पुनः प्रयास करें।"
                    "ERROR_NETWORK_REQUEST_FAILED" -> "इंटरनेट कनेक्शन नहीं है। कृपया नेटवर्क जांचें।"
                    else -> "Firebase लॉगिन त्रुटि (${e.errorCode}): ${e.localizedMessage ?: "कृपया पुनः प्रयास करें।"}"
                }
                _uiState.value = AuthUiState.Error(hindiError)
            } catch (e: Exception) {
                Log.e(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_FAILED | op=FIREBASE_SIGN_IN | code=UNKNOWN | msg=${e.localizedMessage}", e)
                _uiState.value = AuthUiState.Error("लॉगिन विफल रहा: ${e.localizedMessage ?: "कृपया पुनः प्रयास करें।"}")
            } finally {
                if (!authFlowCompleted && _uiState.value is AuthUiState.Loading) {
                    _uiState.value = AuthUiState.Idle
                }
            }
        }
    }

    fun attemptAutoSignIn(context: Context, credentialManager: CredentialManager) {
        if (auth.currentUser != null) {
            val user = auth.currentUser!!
            _isSessionActive.value = true
            loadUserProfile(user.uid, user.email, user.displayName)
            return
        }

        val clientId = try {
            context.getString(R.string.default_web_client_id)
        } catch (e: Exception) {
            return
        }

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(clientId)
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(context, request)
                val credential = result.credential
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
                    val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                    val authResult = auth.signInWithCredential(authCredential).await()
                    val user = authResult.user
                    if (user != null) {
                        Log.d(AUTH_DEBUG_TAG, "FIREBASE_SIGNIN_SUCCESS | op=AUTO_SIGN_IN | uid=${user.uid}")
                        _isSessionActive.value = true
                        loadUserProfile(user.uid, user.email, user.displayName)
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.d(AUTH_DEBUG_TAG, "AUTO_SIGN_IN_SKIPPED | msg=${e.message}")
            }
        }
    }

    fun signOut(context: Context, credentialManager: CredentialManager) {
        profileJob?.cancel()
        profileJob = null
        activeProfileUid = null
        auth.signOut()
        _currentUserProfile.value = null
        _isAdmin.value = false
        _isSessionActive.value = false
        _uiState.value = AuthUiState.Idle
        viewModelScope.launch {
            try {
                credentialManager.clearCredentialState(ClearCredentialStateRequest())
            } catch (e: Exception) {
                Log.e("Auth", "Failed to clear credential state", e)
            }
        }
    }

    fun switchToStudentRole() {
        val user = auth.currentUser
        val isOwner = user?.email.equals("mithleshyadaw0@gmail.com", ignoreCase = true)
        if (!isOwner) {
            _isAdmin.value = false
        }
    }
}
