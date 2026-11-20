package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamResult
import com.example.data.model.ExamTest
import com.example.data.model.UserProfile
import com.example.data.repository.ExamRepository
import com.example.ui.admin.AdminDashboardScreen
import com.example.ui.admin.AdminViewModel
import com.example.ui.analysis.LearningAnalysisScreen
import com.example.ui.auth.AuthScreen
import com.example.ui.auth.AuthViewModel
import com.example.ui.exam.CbtExamScreen
import com.example.ui.exam.ExamInstructionsScreen
import com.example.ui.exam.ExamResultScreen
import com.example.ui.exam.ExamUiState
import com.example.ui.exam.ExamViewModel
import com.example.ui.leaderboard.LeaderboardScreen
import com.example.ui.profile.ProfileScreen
import com.example.ui.splash.SplashScreen
import com.example.ui.student.StudentHomeScreen
import com.example.ui.student.StudentHomeViewModel
import com.example.ui.theme.MathsExamTheme
import com.example.ui.theme.PrimaryBlue
import com.example.ui.videos.RecommendedVideosScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

enum class ScreenState {
    SPLASH,
    AUTH,
    STUDENT_HOME,
    EXAM_INSTRUCTIONS,
    CBT_EXAM,
    EXAM_RESULT,
    LEADERBOARD,
    ADMIN_DASHBOARD,
    PROFILE,
    VIDEOS,
    ANALYSIS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MathsExamTheme {
                AppRootContent()
            }
        }
    }
}

@Composable
fun AppRootContent() {
    val context = LocalContext.current
    val auth = remember { Firebase.auth }
    val credentialManager = remember { CredentialManager.create(context) }
    val repository = remember { ExamRepository(context) }
    val authViewModel = remember { AuthViewModel(repository) }

    var currentUser by remember { mutableStateOf(auth.currentUser) }

    DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            currentUser = user
            if (user != null) {
                authViewModel.loadUserProfile(user.uid, user.email, user.displayName)
            }
        }
        auth.addAuthStateListener(listener)
        onDispose {
            auth.removeAuthStateListener(listener)
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.attemptAutoSignIn(context, credentialManager)
    }

    val userProfile by authViewModel.currentUserProfile.collectAsState()
    val isAdmin by authViewModel.isAdmin.collectAsState()
    val isSessionActive by authViewModel.isSessionActive.collectAsState()

    // Navigation and screen management (starts with animated splash screen)
    var currentScreen by remember { mutableStateOf(ScreenState.SPLASH) }
    var selectedTestForExam by remember { mutableStateOf<ExamTest?>(null) }
    var activeLeaderboardTestId by remember { mutableStateOf("test_railway_math_01") }
    var latestResult by remember { mutableStateOf<ExamResult?>(null) }
    var latestQuestions by remember { mutableStateOf<List<ExamQuestion>>(emptyList()) }

    var examViewModel by remember { mutableStateOf<ExamViewModel?>(null) }

    val activeUid = currentUser?.uid ?: userProfile?.userId ?: "user_default"
    val firebaseUid = currentUser?.uid

    when (currentScreen) {
        ScreenState.SPLASH -> {
            SplashScreen(
                onAnimationFinished = {
                    if (currentUser != null || isSessionActive) {
                        currentScreen = ScreenState.STUDENT_HOME
                    } else {
                        currentScreen = ScreenState.AUTH
                    }
                }
            )
        }
        ScreenState.AUTH -> {
            AuthScreen(
                authViewModel = authViewModel,
                onAdminLoginSuccess = { currentScreen = ScreenState.ADMIN_DASHBOARD },
                onStudentLoginSuccess = { currentScreen = ScreenState.STUDENT_HOME }
            )
        }
        else -> {
            val studentHomeViewModel = remember(activeUid, firebaseUid) {
                StudentHomeViewModel(repository, activeUid)
            }

            when (currentScreen) {
                ScreenState.STUDENT_HOME -> {
                    StudentHomeScreen(
                        viewModel = studentHomeViewModel,
                        userProfile = userProfile,
                        isAdmin = isAdmin,
                        onStartTest = { test ->
                            selectedTestForExam = test
                            val profile = userProfile ?: UserProfile(
                                userId = activeUid,
                                name = currentUser?.displayName ?: "Student",
                                studentId = "STU-${activeUid.take(6).uppercase()}"
                            )
                            val evm = ExamViewModel(
                                repository = repository,
                                studentProfile = profile
                            )
                            examViewModel = evm
                            evm.loadTest(test)
                            currentScreen = ScreenState.EXAM_INSTRUCTIONS
                        },
                        onViewResult = { res ->
                            latestResult = res
                            currentScreen = ScreenState.EXAM_RESULT
                        },
                        onViewResults = {
                            val firstRes = studentHomeViewModel.results.value.firstOrNull()
                            if (firstRes != null) {
                                latestResult = firstRes
                                currentScreen = ScreenState.EXAM_RESULT
                            } else {
                                currentScreen = ScreenState.PROFILE
                            }
                        },
                        onViewLeaderboard = { testId ->
                            activeLeaderboardTestId = testId
                            currentScreen = ScreenState.LEADERBOARD
                        },
                        onViewVideos = { currentScreen = ScreenState.VIDEOS },
                        onViewAnalysis = { currentScreen = ScreenState.ANALYSIS },
                        onOpenAdmin = { currentScreen = ScreenState.ADMIN_DASHBOARD },
                        onOpenProfile = { currentScreen = ScreenState.PROFILE }
                    )
                }
                ScreenState.EXAM_INSTRUCTIONS -> {
                    BackHandler { currentScreen = ScreenState.STUDENT_HOME }
                    val test = selectedTestForExam
                    if (test != null && examViewModel != null) {
                        val existingRes = studentHomeViewModel.getResultForTest(test.id)
                        ExamInstructionsScreen(
                            test = test,
                            existingResult = existingRes,
                            onStartExam = {
                                examViewModel?.startCbtExam(test)
                                currentScreen = ScreenState.CBT_EXAM
                            },
                            onViewResult = { res ->
                                latestResult = res
                                currentScreen = ScreenState.EXAM_RESULT
                            },
                            onBack = { currentScreen = ScreenState.STUDENT_HOME }
                        )
                    } else {
                        currentScreen = ScreenState.STUDENT_HOME
                    }
                }
                ScreenState.CBT_EXAM -> {
                    val evm = examViewModel
                    if (evm != null) {
                        val examState by evm.uiState.collectAsState()
                        when (val state = examState) {
                            is ExamUiState.Loading -> {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator(color = PrimaryBlue)
                                }
                            }
                            is ExamUiState.TakingExam -> {
                                CbtExamScreen(
                                    test = state.test,
                                    currentQuestionIndex = state.currentQuestionIndex,
                                    language = state.language,
                                    remainingSeconds = state.remainingSeconds,
                                    viewModel = evm
                                )
                            }
                            is ExamUiState.ResultReady -> {
                                latestResult = state.result
                                latestQuestions = evm.getQuestionsList()
                                currentScreen = ScreenState.EXAM_RESULT
                            }
                            is ExamUiState.Error -> {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text("त्रुटि: ${state.message}", color = MaterialTheme.colorScheme.error)
                                }
                            }
                            else -> {
                                currentScreen = ScreenState.STUDENT_HOME
                            }
                        }
                    } else {
                        currentScreen = ScreenState.STUDENT_HOME
                    }
                }
                ScreenState.EXAM_RESULT -> {
                    BackHandler { currentScreen = ScreenState.STUDENT_HOME }
                    val res = latestResult
                    val savedQuestionIds by repository.savedQuestionIds.collectAsState()
                    if (res != null) {
                        LaunchedEffect(res.testId) {
                            if (latestQuestions.firstOrNull()?.testId != res.testId) {
                                latestQuestions = repository.getQuestionsForTest(res.testId)
                            }
                        }
                        ExamResultScreen(
                            result = res,
                            questions = latestQuestions,
                            savedQuestionIds = savedQuestionIds,
                            onToggleSaveQuestion = { qId -> repository.toggleSaveQuestion(qId) },
                            onViewLeaderboard = { testId ->
                                activeLeaderboardTestId = testId
                                currentScreen = ScreenState.LEADERBOARD
                            },
                            onBackToHome = { currentScreen = ScreenState.STUDENT_HOME }
                        )
                    } else {
                        currentScreen = ScreenState.STUDENT_HOME
                    }
                }
                ScreenState.LEADERBOARD -> {
                    BackHandler { currentScreen = ScreenState.STUDENT_HOME }
                    LeaderboardScreen(
                        initialTestId = activeLeaderboardTestId,
                        repository = repository,
                        currentStudentUid = activeUid,
                        onBack = { currentScreen = ScreenState.STUDENT_HOME }
                    )
                }
                ScreenState.ADMIN_DASHBOARD -> {
                    BackHandler { currentScreen = ScreenState.STUDENT_HOME }
                    if (!isAdmin) {
                        LaunchedEffect(Unit) {
                            currentScreen = ScreenState.STUDENT_HOME
                        }
                    } else {
                        val adminViewModel = remember(activeUid, firebaseUid) { AdminViewModel(repository) }
                        AdminDashboardScreen(
                            viewModel = adminViewModel,
                            onBack = { currentScreen = ScreenState.STUDENT_HOME }
                        )
                    }
                }
                ScreenState.PROFILE -> {
                    BackHandler { currentScreen = ScreenState.STUDENT_HOME }
                    ProfileScreen(
                        userProfile = userProfile,
                        repository = repository,
                        onSignOut = {
                            selectedTestForExam = null
                            examViewModel = null
                            latestResult = null
                            latestQuestions = emptyList()
                            currentScreen = ScreenState.AUTH
                            authViewModel.signOut(context, credentialManager)
                        },
                        onBack = { currentScreen = ScreenState.STUDENT_HOME }
                    )
                }
                ScreenState.VIDEOS -> {
                    BackHandler { currentScreen = ScreenState.STUDENT_HOME }
                    RecommendedVideosScreen(onBack = { currentScreen = ScreenState.STUDENT_HOME })
                }
                ScreenState.ANALYSIS -> {
                    BackHandler { currentScreen = ScreenState.STUDENT_HOME }
                    LearningAnalysisScreen(
                        currentStudentUid = activeUid,
                        repository = repository,
                        onBack = { currentScreen = ScreenState.STUDENT_HOME }
                    )
                }
                ScreenState.SPLASH, ScreenState.AUTH -> Unit
            }
        }
    }
}
