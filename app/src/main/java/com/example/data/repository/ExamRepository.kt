package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.R
import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamAttempt
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamResult
import com.example.data.model.ExamTest
import com.example.data.model.LeaderboardEntry
import com.example.data.model.UserProfile
import com.example.data.seed.SeedExamData
import com.example.data.util.OperationType
import com.example.data.util.handleFirestoreError
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ExamRepository(private val db: FirebaseFirestore) {

    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    fun isFirebaseSignedIn(): Boolean = auth.currentUser != null

    fun isFirebaseTeacherAdmin(): Boolean {
        val user = auth.currentUser ?: return false
        return user.email.equals("mithleshyadaw0@gmail.com", ignoreCase = true) && user.isEmailVerified
    }

    private fun authenticatedUidOrNull(): String? = auth.currentUser?.uid

    private fun isSignOutPermissionError(error: Throwable): Boolean {
        val firestoreError = generateSequence(error) { it.cause }
            .filterIsInstance<FirebaseFirestoreException>()
            .firstOrNull()
        return firestoreError?.code == FirebaseFirestoreException.Code.PERMISSION_DENIED &&
            auth.currentUser == null
    }

    // -------------------------------------------------------------
    // In-Memory Reactive Cache for instantaneous UI updates
    // -------------------------------------------------------------
    private val localTests = mutableListOf<ExamTest>().apply {
        addAll(SeedExamData.getAllTests())
    }
    private val _testsFlow = MutableStateFlow<List<ExamTest>>(localTests.toList())

    private val localQuestionsMap = mutableMapOf<String, MutableList<ExamQuestion>>()
    private val localAnswerKeysMap = mutableMapOf<String, ExamAnswerKey>()
    private val localAttemptsMap = mutableMapOf<String, ExamAttempt>()
    private val localResults = mutableListOf<ExamResult>()
    private val _localResultsFlow = MutableStateFlow<List<ExamResult>>(emptyList())
    private val _savedQuestionIdsFlow = MutableStateFlow<Set<String>>(emptySet())
    val savedQuestionIds: StateFlow<Set<String>> = _savedQuestionIdsFlow.asStateFlow()

    init {
        // Pre-populate questions & answer keys for all seeded tests
        SeedExamData.getAllTests().forEach { test ->
            val (qList, key) = SeedExamData.getQuestionsForTest(test.id)
            if (qList.isNotEmpty()) {
                localQuestionsMap[test.id] = qList.toMutableList()
                localAnswerKeysMap[test.id] = key
            }
        }
    }

    fun toggleSaveQuestion(questionId: String) {
        val current = _savedQuestionIdsFlow.value
        _savedQuestionIdsFlow.value = if (questionId in current) current - questionId else current + questionId
    }

    data class ContextQuestionItem(
        val test: ExamTest,
        val question: ExamQuestion,
        val correctOption: String,
        val explanationEn: String,
        val explanationHi: String,
        val isUserSaved: Boolean
    )

    fun getHighYieldQuestionsForContext(
        examId: String?,
        examClass: String?
    ): List<ContextQuestionItem> {
        val savedIds = _savedQuestionIdsFlow.value
        val matchingTests = synchronized(localTests) {
            localTests.filter { test ->
                if (!test.isPublished) return@filter false
                val matchesExam = examId == null || examId == "All" || test.category.equals(examId, ignoreCase = true)
                val matchesClass = examClass.isNullOrBlank() || test.examClass.equals(examClass, ignoreCase = true)
                matchesExam && matchesClass
            }
        }
        val items = mutableListOf<ContextQuestionItem>()
        matchingTests.forEach { test ->
            val qList = localQuestionsMap[test.id] ?: emptyList()
            val key = localAnswerKeysMap[test.id]
            qList.forEachIndexed { index, q ->
                val isSaved = q.id in savedIds
                // Include all user-saved questions plus the first high-yield question per test so list is rich & authentic
                if (isSaved || index == 0) {
                    items.add(
                        ContextQuestionItem(
                            test = test,
                            question = q,
                            correctOption = key?.keys?.get(q.id) ?: "-",
                            explanationEn = key?.explanationsEn?.get(q.id) ?: "",
                            explanationHi = key?.explanationsHi?.get(q.id) ?: "",
                            isUserSaved = isSaved
                        )
                    )
                }
            }
        }
        return items.sortedByDescending { it.isUserSaved }
    }

    private fun normalizeRemoteTest(remote: ExamTest): ExamTest {
        return when (remote.id) {
            "test_jnv_sainik_03" -> remote.copy(
                category = "Navodaya",
                examClass = remote.examClass.ifBlank { "Class 6" },
                testType = remote.testType.ifBlank { "FULL_LENGTH" },
                subType = remote.subType.ifBlank { "Mock Test" }
            )
            "test_railway_math_01", "test_ssc_cgl_chsl_02" -> remote.copy(
                testType = remote.testType.ifBlank { "FULL_LENGTH" },
                subType = remote.subType.ifBlank { "Mock Test" }
            )
            else -> remote
        }
    }

    // -------------------------------------------------------------
    // User Profiles
    // -------------------------------------------------------------
    sealed interface ProfileFetchResult {
        data class Found(val profile: UserProfile) : ProfileFetchResult
        object NotFound : ProfileFetchResult
        data class Error(val code: String, val message: String, val path: String) : ProfileFetchResult
    }

    suspend fun fetchStudentProfileOnce(userId: String): ProfileFetchResult {
        val authUid = authenticatedUidOrNull()
        val path = "users/$userId"
        if (authUid == null || userId.isBlank() || (authUid != userId && !isFirebaseTeacherAdmin())) {
            Log.e(
                "AuthDebug",
                "STUDENT_PROFILE_FETCH_FAILED | op=GET | path=$path | code=UNAUTHENTICATED | msg=Auth session missing or UID mismatch"
            )
            return ProfileFetchResult.Error(
                code = "UNAUTHENTICATED",
                message = "Firebase सत्र सक्रिय नहीं है। कृपया पुनः लॉगिन करें।",
                path = path
            )
        }
        Log.d("AuthDebug", "STUDENT_PROFILE_FETCH_STARTED | op=GET | uid=$authUid | path=$path")
        return try {
            val snapshot = db.collection("users").document(userId).get().await()
            if (!snapshot.exists()) {
                Log.w(
                    "AuthDebug",
                    "STUDENT_PROFILE_FETCH_FAILED | op=GET | uid=$authUid | path=$path | code=MISSING_DOCUMENT | msg=Profile document does not exist"
                )
                ProfileFetchResult.NotFound
            } else {
                val profile = snapshot.toObject(UserProfile::class.java)
                if (profile == null) {
                    Log.e(
                        "AuthDebug",
                        "STUDENT_PROFILE_FETCH_FAILED | op=GET | uid=$authUid | path=$path | code=DESERIALIZATION_ERROR | msg=Snapshot could not be parsed into UserProfile"
                    )
                    ProfileFetchResult.Error(
                        code = "DESERIALIZATION_ERROR",
                        message = "छात्र प्रोफ़ाइल डेटा पढ़ने में त्रुटि हुई।",
                        path = path
                    )
                } else {
                    Log.d(
                        "AuthDebug",
                        "STUDENT_PROFILE_FETCH_SUCCESS | op=GET | uid=$authUid | path=$path | role=${profile.role}"
                    )
                    ProfileFetchResult.Found(profile)
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseFirestoreException) {
            handleFirestoreError(e, OperationType.GET, path)
            Log.e(
                "AuthDebug",
                "STUDENT_PROFILE_FETCH_FAILED | op=GET | uid=$authUid | path=$path | code=${e.code.name} | msg=${e.localizedMessage}"
            )
            ProfileFetchResult.Error(
                code = e.code.name,
                message = e.localizedMessage ?: "Firestore error (${e.code.name})",
                path = path
            )
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.GET, path)
            Log.e(
                "AuthDebug",
                "STUDENT_PROFILE_FETCH_FAILED | op=GET | uid=$authUid | path=$path | code=UNKNOWN | msg=${e.localizedMessage}"
            )
            ProfileFetchResult.Error(
                code = "UNKNOWN",
                message = e.localizedMessage ?: "Unknown profile fetch error",
                path = path
            )
        }
    }

    fun observeUserProfile(userId: String): Flow<UserProfile?> = flow {
        val authUid = authenticatedUidOrNull()
        if (authUid == null || userId.isBlank() || (authUid != userId && !isFirebaseTeacherAdmin())) {
            emit(null)
            return@flow
        }
        val path = "users/$userId"
        emitAll(
            db.collection("users").document(userId)
                .snapshots()
                .map { snapshot ->
                    if (snapshot.exists()) snapshot.toObject(UserProfile::class.java) else null
                }
                .catch { error ->
                    val firestoreError = generateSequence(error) { it.cause }
                        .filterIsInstance<FirebaseFirestoreException>()
                        .firstOrNull()
                    if (firestoreError == null && error is CancellationException) throw error
                    if (!isSignOutPermissionError(error)) {
                        val exceptionToLog = firestoreError ?: (error as? Exception)
                        if (exceptionToLog != null) {
                            handleFirestoreError(exceptionToLog, OperationType.GET, path)
                        }
                    }
                    throw error
                }
        )
    }

    suspend fun saveUserProfile(profile: UserProfile): Result<UserProfile> {
        val uid = authenticatedUidOrNull()
            ?: return Result.failure(IllegalStateException("कृपया पहले Firebase से लॉगिन करें।"))
        val docRef = db.collection("users").document(uid)
        return try {
            val resolvedRole = if (profile.role == "admin" && isFirebaseTeacherAdmin()) "admin" else "student"
            val resolvedName = profile.name.ifBlank { "Student" }.take(100)
            val resolvedStudentId = profile.studentId.ifBlank { "STU-${uid.take(6).uppercase()}" }.take(50)
            val resolvedEmail = (auth.currentUser?.email ?: profile.email).take(200)
            val data = mapOf(
                "userId" to uid,
                "role" to resolvedRole,
                "name" to resolvedName,
                "studentId" to resolvedStudentId,
                "studentClass" to profile.studentClass.take(100),
                "email" to resolvedEmail,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(data).await()
            val savedProfile = profile.copy(
                id = uid,
                userId = uid,
                role = resolvedRole,
                name = resolvedName,
                studentId = resolvedStudentId,
                email = resolvedEmail
            )
            Log.d("AuthDebug", "STUDENT_PROFILE_SAVE_SUCCESS | op=SET | uid=$uid | path=${docRef.path}")
            Result.success(savedProfile)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            if (!isSignOutPermissionError(e)) {
                handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            }
            Log.e(
                "AuthDebug",
                "STUDENT_PROFILE_SAVE_FAILED | op=SET | uid=$uid | path=${docRef.path} | msg=${e.localizedMessage}"
            )
            Result.failure(e)
        }
    }

    // -------------------------------------------------------------
    // Tests Management (Reactive In-Memory + Background Cloud Sync)
    // -------------------------------------------------------------
    fun observeTests(): Flow<List<ExamTest>> = flow {
        if (!isFirebaseSignedIn()) {
            emitAll(_testsFlow)
            return@flow
        }

        coroutineScope {
            launch {
                db.collection("tests")
                    .snapshots()
                    .catch { error ->
                        if (error is CancellationException) throw error
                        if (!isSignOutPermissionError(error)) {
                            handleFirestoreError(
                                error as? Exception ?: Exception(error),
                                OperationType.LIST,
                                "tests"
                            )
                        }
                    }
                    .collect { snapshot ->
                        val remoteTests = snapshot.toObjects(ExamTest::class.java)
                        if (remoteTests.isNotEmpty()) {
                            synchronized(localTests) {
                                remoteTests.forEach { rawRemote ->
                                    val remote = normalizeRemoteTest(rawRemote)
                                    if (remote.id.isNotBlank()) {
                                        val idx = localTests.indexOfFirst { it.id == remote.id }
                                        if (idx >= 0) {
                                            localTests[idx] = remote
                                        } else {
                                            localTests.add(remote)
                                        }
                                    }
                                }
                                _testsFlow.value = localTests.toList()
                            }
                        }
                    }
            }
            emitAll(_testsFlow)
        }
    }

    suspend fun getTestById(testId: String): ExamTest? {
        synchronized(localTests) {
            val local = localTests.find { it.id == testId }
            if (local != null) return local
        }
        if (!isFirebaseSignedIn()) return null
        val docRef = db.collection("tests").document(testId)
        return try {
            val doc = docRef.get().await()
            val remote = doc.toObject(ExamTest::class.java)
            if (remote != null) {
                synchronized(localTests) {
                    if (localTests.none { it.id == remote.id }) {
                        localTests.add(remote)
                        _testsFlow.value = localTests.toList()
                    }
                }
            }
            remote
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            null
        }
    }

    suspend fun createTest(test: ExamTest) {
        val testWithId = if (test.id.isBlank()) test.copy(id = "test_${System.currentTimeMillis()}") else test
        synchronized(localTests) {
            val idx = localTests.indexOfFirst { it.id == testWithId.id }
            if (idx >= 0) {
                localTests[idx] = testWithId
            } else {
                localTests.add(0, testWithId)
            }
            _testsFlow.value = localTests.toList()
        }

        // Initialize local questions cache with empty list for custom new test
        if (!localQuestionsMap.containsKey(testWithId.id)) {
            localQuestionsMap[testWithId.id] = mutableListOf()
            localAnswerKeysMap[testWithId.id] = ExamAnswerKey(testId = testWithId.id)
        }

        // Only verified Firebase teacher/admin has permission to write /tests in Firestore
        if (!isFirebaseTeacherAdmin()) return

        try {
            val docRef = db.collection("tests").document(testWithId.id)
            val data = mapOf(
                "id" to testWithId.id,
                "title" to testWithId.title.ifBlank { "Untitled Test" }.take(200),
                "titleHindi" to testWithId.titleHindi.take(200),
                "subject" to testWithId.subject.ifBlank { "Mathematics" }.take(100),
                "category" to testWithId.category.take(100),
                "examClass" to testWithId.examClass,
                "testType" to testWithId.testType,
                "subType" to testWithId.subType,
                "chapter" to testWithId.chapter,
                "examYear" to testWithId.examYear,
                "isFree" to testWithId.isFree,
                "totalQuestions" to testWithId.totalQuestions.coerceAtLeast(0),
                "durationMinutes" to testWithId.durationMinutes.coerceAtLeast(1),
                "marksPerQuestion" to testWithId.marksPerQuestion,
                "negativeMarking" to testWithId.negativeMarking,
                "instructionsEn" to testWithId.instructionsEn,
                "instructionsHi" to testWithId.instructionsHi,
                "allowRetakes" to testWithId.allowRetakes,
                "showExplanations" to testWithId.showExplanations,
                "isPublished" to testWithId.isPublished,
                "createdBy" to (auth.currentUser?.uid ?: "mithlesh_sir"),
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(data).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // Local state remains active
        }
    }

    suspend fun updateTestPublishStatus(testId: String, isPublished: Boolean) {
        synchronized(localTests) {
            val idx = localTests.indexOfFirst { it.id == testId }
            if (idx >= 0) {
                localTests[idx] = localTests[idx].copy(isPublished = isPublished)
                _testsFlow.value = localTests.toList()
            }
        }

        if (!isFirebaseTeacherAdmin()) return

        try {
            val docRef = db.collection("tests").document(testId)
            docRef.update(
                mapOf(
                    "isPublished" to isPublished,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // Silently keep local state so teacher's switch toggles instantly
        }
    }

    suspend fun deleteTest(testId: String) {
        synchronized(localTests) {
            localTests.removeAll { it.id == testId }
            _testsFlow.value = localTests.toList()
        }
        localQuestionsMap.remove(testId)
        localAnswerKeysMap.remove(testId)

        if (!isFirebaseTeacherAdmin()) return

        try {
            val docRef = db.collection("tests").document(testId)
            docRef.delete().await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // Silently keep local state
        }
    }

    // -------------------------------------------------------------
    // Questions & Answer Key (Reactive In-Memory + Background Cloud Sync)
    // -------------------------------------------------------------
    suspend fun getQuestionsForTest(testId: String): List<ExamQuestion> {
        val cached = localQuestionsMap[testId]
        if (cached != null) {
            return cached.toList()
        }

        if (!isFirebaseSignedIn()) {
            val seedData = SeedExamData.getQuestionsForTest(testId)
            localQuestionsMap[testId] = seedData.first.toMutableList()
            localAnswerKeysMap[testId] = seedData.second
            return seedData.first
        }

        val colRef = db.collection("tests").document(testId).collection("questions")
        return try {
            val snapshot = colRef.orderBy("questionIndex", Query.Direction.ASCENDING).get().await()
            if (snapshot.isEmpty) {
                val seedData = SeedExamData.getQuestionsForTest(testId)
                localQuestionsMap[testId] = seedData.first.toMutableList()
                localAnswerKeysMap[testId] = seedData.second
                seedData.first
            } else {
                val list = snapshot.toObjects(ExamQuestion::class.java)
                localQuestionsMap[testId] = list.toMutableList()
                list
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val seedData = SeedExamData.getQuestionsForTest(testId)
            localQuestionsMap[testId] = seedData.first.toMutableList()
            localAnswerKeysMap[testId] = seedData.second
            seedData.first
        }
    }

    suspend fun getAnswerKeyForTest(testId: String): ExamAnswerKey? {
        val cached = localAnswerKeysMap[testId]
        if (cached != null) {
            return cached
        }

        if (!isFirebaseSignedIn()) {
            val seedKey = SeedExamData.getQuestionsForTest(testId).second
            localAnswerKeysMap[testId] = seedKey
            return seedKey
        }

        val colRef = db.collection("tests").document(testId).collection("answerKeys")
        return try {
            val snapshot = colRef.get().await()
            if (snapshot.isEmpty) {
                val seedKey = SeedExamData.getQuestionsForTest(testId).second
                localAnswerKeysMap[testId] = seedKey
                seedKey
            } else {
                val key = snapshot.documents.firstOrNull()?.toObject(ExamAnswerKey::class.java)
                    ?: SeedExamData.getQuestionsForTest(testId).second
                localAnswerKeysMap[testId] = key
                key
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val seedKey = SeedExamData.getQuestionsForTest(testId).second
            localAnswerKeysMap[testId] = seedKey
            seedKey
        }
    }

    suspend fun saveQuestionsAndKey(testId: String, questions: List<ExamQuestion>, answerKey: ExamAnswerKey) {
        // 1. Immediately update in-memory cache
        localQuestionsMap[testId] = questions.toMutableList()
        localAnswerKeysMap[testId] = answerKey

        // 2. Immediately update totalQuestions count on the test
        synchronized(localTests) {
            val idx = localTests.indexOfFirst { it.id == testId }
            if (idx >= 0) {
                localTests[idx] = localTests[idx].copy(totalQuestions = questions.size)
                _testsFlow.value = localTests.toList()
            }
        }

        // 3. Only verified Firebase teacher/admin writes questions/answerKeys to Firestore
        if (!isFirebaseTeacherAdmin()) return

        try {
            val batch = db.batch()
            val questionsCol = db.collection("tests").document(testId).collection("questions")
            questions.forEach { q ->
                val doc = questionsCol.document(q.id)
                batch.set(
                    doc,
                    mapOf(
                        "id" to q.id,
                        "testId" to testId,
                        "questionIndex" to q.questionIndex,
                        "questionEn" to q.questionEn.ifBlank { "Question" },
                        "questionHi" to q.questionHi,
                        "optionsEn" to q.optionsEn,
                        "optionsHi" to q.optionsHi,
                        "marks" to q.marks,
                        "imageUrl" to q.imageUrl,
                        "createdAt" to FieldValue.serverTimestamp()
                    )
                )
            }

            val keyDoc = db.collection("tests").document(testId).collection("answerKeys").document("${testId}_key")
            batch.set(
                keyDoc,
                mapOf(
                    "id" to keyDoc.id,
                    "testId" to testId,
                    "keys" to answerKey.keys,
                    "explanationsEn" to answerKey.explanationsEn,
                    "explanationsHi" to answerKey.explanationsHi,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )

            batch.commit().await()
            db.collection("tests").document(testId).update("totalQuestions", questions.size).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // Local state remains 100% saved and intact
        }
    }

    // -------------------------------------------------------------
    // Exam Attempt & Auto-Save
    // -------------------------------------------------------------
    suspend fun saveAttempt(attempt: ExamAttempt) {
        val uid = authenticatedUidOrNull()
        val cacheKey = "${uid ?: "local"}_${attempt.testId}"
        synchronized(localAttemptsMap) {
            localAttemptsMap[cacheKey] = attempt.copy(studentUid = uid ?: attempt.studentUid)
        }
        if (uid == null) return

        val docRef = db.collection("attempts").document(attempt.id)
        try {
            val data = mapOf(
                "id" to attempt.id,
                "testId" to attempt.testId,
                "studentUid" to uid,
                "studentName" to attempt.studentName,
                "studentId" to attempt.studentId,
                "status" to attempt.status,
                "answers" to attempt.answers,
                "reviewFlags" to attempt.reviewFlags,
                "remainingSeconds" to attempt.remainingSeconds,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(data).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            if (!isSignOutPermissionError(e)) {
                handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            }
        }
    }

    suspend fun getActiveAttempt(testId: String): ExamAttempt? {
        val uid = authenticatedUidOrNull()
        if (uid == null) {
            return synchronized(localAttemptsMap) {
                localAttemptsMap["local_$testId"]?.takeIf { it.status == "in_progress" }
            }
        }

        return try {
            val snapshot = db.collection("attempts")
                .whereEqualTo("studentUid", uid)
                .whereEqualTo("testId", testId)
                .whereEqualTo("status", "in_progress")
                .limit(1)
                .get()
                .await()
            snapshot.documents.firstOrNull()?.toObject(ExamAttempt::class.java)
                ?: synchronized(localAttemptsMap) {
                    localAttemptsMap["${uid}_$testId"]?.takeIf { it.status == "in_progress" }
                }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            if (!isSignOutPermissionError(e)) {
                handleFirestoreError(e, OperationType.LIST, "attempts")
            }
            synchronized(localAttemptsMap) {
                localAttemptsMap["${uid}_$testId"]?.takeIf { it.status == "in_progress" }
            }
        }
    }

    // -------------------------------------------------------------
    // Submission, Scoring & Leaderboard
    // -------------------------------------------------------------
    suspend fun submitExam(
        test: ExamTest,
        attempt: ExamAttempt,
        answerKey: ExamAnswerKey,
        questions: List<ExamQuestion>,
        studentProfile: UserProfile
    ): ExamResult {
        val uid = authenticatedUidOrNull() ?: studentProfile.userId.ifBlank { "local_student" }
        var correctCount = 0
        var incorrectCount = 0
        var unattemptedCount = 0
        var totalScore = 0.0

        questions.forEach { q ->
            val userChoice = attempt.answers[q.id]
            val correctChoice = answerKey.keys[q.id]
            if (userChoice.isNullOrBlank()) {
                unattemptedCount++
            } else if (userChoice.equals(correctChoice, ignoreCase = true)) {
                correctCount++
                totalScore += test.marksPerQuestion
            } else {
                incorrectCount++
                totalScore -= test.negativeMarking
            }
        }

        val finalScore = String.format("%.2f", totalScore).toDouble()
        val maxMarks = questions.size * test.marksPerQuestion
        val percentage = if (maxMarks > 0) String.format("%.1f", (finalScore / maxMarks) * 100.0).toDouble() else 0.0
        val attemptedTotal = correctCount + incorrectCount
        val accuracy = if (attemptedTotal > 0) String.format("%.1f", (correctCount.toDouble() / attemptedTotal) * 100.0).toDouble() else 0.0

        val totalTimeAllowed = test.durationMinutes * 60
        val timeTakenSeconds = (totalTimeAllowed - attempt.remainingSeconds).coerceAtLeast(1)

        val resultId = "res_${test.id}_${uid}_${System.currentTimeMillis()}"
        val resolvedName = studentProfile.name.ifBlank { auth.currentUser?.displayName ?: "Student" }
        val result = ExamResult(
            id = resultId,
            testId = test.id,
            testTitle = test.title,
            examCategory = test.category,
            examClass = test.examClass,
            testType = test.testType,
            subject = test.subject,
            studentUid = uid,
            studentName = resolvedName,
            studentId = studentProfile.studentId.ifBlank { "STU-${uid.take(6).uppercase()}" },
            studentClass = studentProfile.studentClass,
            totalQuestions = questions.size,
            correctAnswers = correctCount,
            incorrectAnswers = incorrectCount,
            unattempted = unattemptedCount,
            totalMarks = finalScore,
            maxMarks = maxMarks,
            percentage = percentage,
            accuracy = accuracy,
            timeTakenSeconds = timeTakenSeconds,
            userAnswers = attempt.answers,
            correctKeys = answerKey.keys,
            explanationsEn = answerKey.explanationsEn,
            explanationsHi = answerKey.explanationsHi,
            submittedAt = Timestamp.now()
        )

        // Immediately record locally so single-attempt & My Results update reliably
        synchronized(localResults) {
            localResults.removeAll { it.testId == result.testId && it.studentUid == uid }
            localResults.add(0, result)
            _localResultsFlow.value = localResults.toList()
        }
        synchronized(localAttemptsMap) {
            localAttemptsMap.remove("local_${test.id}")
            localAttemptsMap.remove("${uid}_${test.id}")
        }

        val firebaseUid = authenticatedUidOrNull() ?: return result

        try {
            // Upsert attempt as submitted (handles quick submissions <15s before first auto-save)
            val attemptId = attempt.id.ifBlank { "att_${test.id}_${firebaseUid}_${System.currentTimeMillis()}" }
            val attemptRef = db.collection("attempts").document(attemptId)
            attemptRef.set(
                mapOf(
                    "id" to attemptId,
                    "testId" to test.id,
                    "studentUid" to firebaseUid,
                    "studentName" to resolvedName,
                    "studentId" to result.studentId,
                    "status" to "submitted",
                    "answers" to attempt.answers,
                    "reviewFlags" to attempt.reviewFlags,
                    "remainingSeconds" to attempt.remainingSeconds,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "submittedAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()

            // Save result
            val resultRef = db.collection("results").document(resultId)
            resultRef.set(
                mapOf(
                    "id" to result.id,
                    "testId" to result.testId,
                    "testTitle" to result.testTitle,
                    "examCategory" to result.examCategory,
                    "examClass" to result.examClass,
                    "testType" to result.testType,
                    "subject" to result.subject,
                    "studentUid" to firebaseUid,
                    "studentName" to result.studentName,
                    "studentId" to result.studentId,
                    "studentClass" to result.studentClass,
                    "totalQuestions" to result.totalQuestions,
                    "correctAnswers" to result.correctAnswers,
                    "incorrectAnswers" to result.incorrectAnswers,
                    "unattempted" to result.unattempted,
                    "totalMarks" to result.totalMarks,
                    "maxMarks" to result.maxMarks,
                    "percentage" to result.percentage,
                    "accuracy" to result.accuracy,
                    "timeTakenSeconds" to result.timeTakenSeconds,
                    "userAnswers" to result.userAnswers,
                    "correctKeys" to result.correctKeys,
                    "explanationsEn" to result.explanationsEn,
                    "explanationsHi" to result.explanationsHi,
                    "submittedAt" to FieldValue.serverTimestamp(),
                    "createdAt" to FieldValue.serverTimestamp()
                )
            ).await()

            // Update Leaderboard entry for this student
            val leaderRef = db.collection("leaderboards").document(test.id).collection("entries").document(firebaseUid)
            leaderRef.set(
                mapOf(
                    "testId" to test.id,
                    "studentUid" to firebaseUid,
                    "studentName" to resolvedName,
                    "studentId" to result.studentId,
                    "studentClass" to result.studentClass,
                    "score" to result.totalMarks,
                    "percentage" to result.percentage,
                    "accuracy" to result.accuracy,
                    "timeTakenSeconds" to result.timeTakenSeconds,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            if (!isSignOutPermissionError(e)) {
                handleFirestoreError(e, OperationType.CREATE, "results")
            }
        }

        return result
    }

    // -------------------------------------------------------------
    // Results & Leaderboards Observation
    // -------------------------------------------------------------
    fun observeStudentResults(studentUid: String): Flow<List<ExamResult>> = flow {
        val authUid = authenticatedUidOrNull()
        val canQueryRemote = authUid != null &&
            studentUid.isNotBlank() &&
            (studentUid == authUid || isFirebaseTeacherAdmin())

        if (!canQueryRemote) {
            emitAll(
                _localResultsFlow.map { local ->
                    local.filter { studentUid.isBlank() || it.studentUid == studentUid }
                        .sortedByDescending { it.submittedAt?.seconds ?: 0L }
                }
            )
            return@flow
        }

        val path = "results"
        val remoteFlow = db.collection("results")
            .whereEqualTo("studentUid", studentUid)
            .snapshots()
            .map { snapshot -> snapshot.toObjects(ExamResult::class.java) }
            .catch { error ->
                if (error is CancellationException) throw error
                if (!isSignOutPermissionError(error)) {
                    handleFirestoreError(error as? Exception ?: Exception(error), OperationType.LIST, path)
                }
                emit(emptyList())
            }

        emitAll(
            combine(remoteFlow, _localResultsFlow) { remote, local ->
                val userLocal = local.filter { it.studentUid == studentUid }
                (remote + userLocal.filter { lr -> remote.none { r -> r.testId == lr.testId } })
                    .sortedByDescending { it.submittedAt?.seconds ?: 0L }
            }
        )
    }

    suspend fun getStudentResultForTest(studentUid: String, testId: String): ExamResult? {
        synchronized(localResults) {
            val local = localResults.firstOrNull { it.studentUid == studentUid && it.testId == testId }
            if (local != null) return local
        }
        val authUid = authenticatedUidOrNull() ?: return null
        if (studentUid.isBlank() || (studentUid != authUid && !isFirebaseTeacherAdmin())) return null

        return try {
            val snapshot = db.collection("results")
                .whereEqualTo("studentUid", studentUid)
                .whereEqualTo("testId", testId)
                .limit(1)
                .get()
                .await()
            snapshot.documents.firstOrNull()?.toObject(ExamResult::class.java)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            null
        }
    }

    fun observeAllResults(): Flow<List<ExamResult>> = flow {
        if (!isFirebaseTeacherAdmin()) {
            emitAll(
                _localResultsFlow.map { list ->
                    list.sortedByDescending { it.submittedAt?.seconds ?: 0L }
                }
            )
            return@flow
        }

        val path = "results"
        val remoteFlow = db.collection("results")
            .snapshots()
            .map { snapshot -> snapshot.toObjects(ExamResult::class.java) }
            .catch { error ->
                if (error is CancellationException) throw error
                if (!isSignOutPermissionError(error)) {
                    handleFirestoreError(error as? Exception ?: Exception(error), OperationType.LIST, path)
                }
                emit(emptyList())
            }

        emitAll(
            combine(remoteFlow, _localResultsFlow) { remote, local ->
                (remote + local.filter { lr -> remote.none { r -> r.id == lr.id } })
                    .sortedByDescending { it.submittedAt?.seconds ?: 0L }
            }
        )
    }

    fun observeLeaderboard(testId: String): Flow<List<LeaderboardEntry>> = flow {
        if (!isFirebaseSignedIn()) {
            emitAll(
                _localResultsFlow.map { results ->
                    results.filter { it.testId == testId }
                        .sortedWith(
                            compareByDescending<ExamResult> { it.totalMarks }
                                .thenByDescending { it.accuracy }
                                .thenBy { it.timeTakenSeconds }
                        )
                        .mapIndexed { index, r ->
                            LeaderboardEntry(
                                id = r.studentUid,
                                testId = r.testId,
                                studentUid = r.studentUid,
                                studentName = r.studentName,
                                studentId = r.studentId,
                                studentClass = r.studentClass,
                                score = r.totalMarks,
                                percentage = r.percentage,
                                accuracy = r.accuracy,
                                timeTakenSeconds = r.timeTakenSeconds,
                                rank = index + 1
                            )
                        }
                }
            )
            return@flow
        }

        val path = "leaderboards/$testId/entries"
        emitAll(
            db.collection("leaderboards").document(testId).collection("entries")
                .snapshots()
                .map { snapshot ->
                    val entries = snapshot.toObjects(LeaderboardEntry::class.java)
                    val sorted = entries.sortedWith(
                        compareByDescending<LeaderboardEntry> { it.score }
                            .thenByDescending { it.accuracy }
                            .thenBy { it.timeTakenSeconds }
                    )
                    sorted.mapIndexed { index, entry ->
                        entry.copy(rank = index + 1)
                    }
                }
                .catch { error ->
                    if (error is CancellationException) throw error
                    if (!isSignOutPermissionError(error)) {
                        handleFirestoreError(error as? Exception ?: Exception(error), OperationType.LIST, path)
                    }
                    emit(emptyList())
                }
        )
    }

    // -------------------------------------------------------------
    // Seeding & CSV Import
    // -------------------------------------------------------------
    suspend fun seedInitialDataIfNeeded() {
        // Only verified Firebase teacher/admin can read/seed /tests in Firestore.
        // Students already receive all seeded tests & questions via localTests / SeedExamData.
        if (!isFirebaseTeacherAdmin()) return

        val existingEmpty = try {
            db.collection("tests").limit(1).get().await().isEmpty
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            if (!isSignOutPermissionError(e)) {
                handleFirestoreError(e, OperationType.LIST, "tests")
            }
            false
        }

        if (!existingEmpty) return

        try {
            SeedExamData.getStandardTests().forEach { test ->
                createTest(test)
            }
            val (qList, key) = SeedExamData.getQuestionsForRailway01()
            saveQuestionsAndKey("test_railway_math_01", qList, key)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            if (!isSignOutPermissionError(e)) {
                handleFirestoreError(e, OperationType.CREATE, "tests")
            }
        }
    }

    suspend fun importQuestionsFromCsv(
        testId: String,
        csvText: String,
        append: Boolean = false
    ): Pair<Int, Int> {
        val lines = csvText.lines().map { it.trimEnd() }.filter { it.isNotBlank() }
        if (lines.isEmpty()) return Pair(0, 0)

        // Determine if first row is a header
        val firstLine = lines.first()
        val isHeader = firstLine.contains("Question", ignoreCase = true) ||
                firstLine.contains("प्रश्न", ignoreCase = true) ||
                firstLine.contains("Option", ignoreCase = true) ||
                firstLine.contains("Correct", ignoreCase = true)

        val dataRows = if (isHeader) lines.drop(1) else lines
        if (dataRows.isEmpty()) return Pair(0, 0)

        val existingQuestions = if (append) getQuestionsForTest(testId).toMutableList() else mutableListOf()
        val existingKey = if (append) getAnswerKeyForTest(testId) ?: ExamAnswerKey(testId = testId) else ExamAnswerKey(testId = testId)

        val updatedKeys = existingKey.keys.toMutableMap()
        val updatedExpEn = existingKey.explanationsEn.toMutableMap()
        val updatedExpHi = existingKey.explanationsHi.toMutableMap()

        var successCount = 0
        var failCount = 0

        dataRows.forEachIndexed { rowIdx, line ->
            try {
                val cols = parseDelimitedLine(line)
                if (cols.size >= 5) {
                    val qEn = cols[0]
                    // If Hindi column is omitted or fewer columns, adapt gracefully
                    val hasHindiCol = cols.size >= 7 && (cols[1].any { it in '\u0900'..'\u097F' } || cols[1].length > 3)
                    val qHi = if (hasHindiCol) cols[1] else qEn
                    val colShift = if (hasHindiCol) 0 else -1

                    val rawOpA = cols.getOrElse(2 + colShift) { "" }
                    val rawOpB = cols.getOrElse(3 + colShift) { "" }
                    val rawOpC = cols.getOrElse(4 + colShift) { "" }
                    val rawOpD = cols.getOrElse(5 + colShift) { "" }

                    val opA = formatOptionLabel("A", rawOpA)
                    val opB = formatOptionLabel("B", rawOpB)
                    val opC = formatOptionLabel("C", rawOpC)
                    val opD = formatOptionLabel("D", rawOpD)

                    val rawCorrect = cols.getOrElse(6 + colShift) { "A" }.trim()
                    val correct = normalizeCorrectOption(rawCorrect)

                    val expEn = cols.getOrElse(7 + colShift) { "" }
                    val expHi = cols.getOrElse(8 + colShift) { expEn }
                    val marks = cols.getOrNull(9 + colShift)?.toDoubleOrNull() ?: 1.0

                    val currentIndex = existingQuestions.size
                    val qId = "${testId}_q${System.currentTimeMillis()}_${rowIdx}_${currentIndex}"

                    val question = ExamQuestion(
                        id = qId,
                        testId = testId,
                        questionIndex = currentIndex,
                        questionEn = qEn,
                        questionHi = qHi.ifBlank { qEn },
                        optionsEn = listOf(opA, opB, opC, opD),
                        optionsHi = listOf(opA, opB, opC, opD),
                        marks = marks
                    )
                    existingQuestions.add(question)
                    updatedKeys[qId] = correct
                    updatedExpEn[qId] = expEn
                    updatedExpHi[qId] = expHi
                    successCount++
                } else {
                    failCount++
                }
            } catch (e: Exception) {
                failCount++
            }
        }

        if (successCount > 0) {
            val key = ExamAnswerKey(
                id = "${testId}_key",
                testId = testId,
                keys = updatedKeys,
                explanationsEn = updatedExpEn,
                explanationsHi = updatedExpHi
            )
            saveQuestionsAndKey(testId, existingQuestions, key)
        }

        return Pair(successCount, failCount)
    }

    private fun formatOptionLabel(letter: String, rawText: String): String {
        val trimmed = rawText.trim()
        val regex = Regex("^[A-Da-d][\\)\\.\\:\\-]?\\s*")
        val clean = if (regex.containsMatchIn(trimmed)) trimmed.replace(regex, "") else trimmed
        return "$letter) ${clean.ifBlank { "-" }}"
    }

    private fun normalizeCorrectOption(raw: String): String {
        val clean = raw.trim().uppercase()
        return when {
            clean.startsWith("A") || clean == "1" || clean.contains("OPTION A") -> "A"
            clean.startsWith("B") || clean == "2" || clean.contains("OPTION B") -> "B"
            clean.startsWith("C") || clean == "3" || clean.contains("OPTION C") -> "C"
            clean.startsWith("D") || clean == "4" || clean.contains("OPTION D") -> "D"
            else -> "A"
        }
    }

    fun parseDelimitedLine(line: String): List<String> {
        val delimiter = when {
            line.contains('\t') -> '\t'
            line.contains(';') && !line.contains(',') -> ';'
            else -> ','
        }

        if (delimiter == '\t') {
            return line.split('\t').map { it.trim().removeSurrounding("\"").trim() }
        }

        val result = mutableListOf<String>()
        var cur = StringBuilder()
        var inQuotes = false
        var i = 0
        while (i < line.length) {
            val c = line[i]
            if (c == '"') {
                if (inQuotes && i + 1 < line.length && line[i + 1] == '"') {
                    cur.append('"')
                    i++
                } else {
                    inQuotes = !inQuotes
                }
            } else if (c == delimiter && !inQuotes) {
                result.add(cur.toString().trim())
                cur = StringBuilder()
            } else {
                cur.append(c)
            }
            i++
        }
        result.add(cur.toString().trim())
        return result
    }
}
