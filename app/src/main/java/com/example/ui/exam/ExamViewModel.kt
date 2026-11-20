package com.example.ui.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CbtStatus
import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamAttempt
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamResult
import com.example.data.model.ExamTest
import com.example.data.model.UserProfile
import com.example.data.repository.ExamRepository
import com.example.data.seed.SeedExamData
import com.google.firebase.Timestamp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ExamUiState {
    object Loading : ExamUiState
    data class InInstructions(val test: ExamTest) : ExamUiState
    data class TakingExam(
        val test: ExamTest,
        val currentQuestionIndex: Int,
        val language: String, // "EN" or "HI"
        val remainingSeconds: Int,
        val isPaletteOpen: Boolean = false
    ) : ExamUiState
    data class ResultReady(val result: ExamResult) : ExamUiState
    data class Error(val message: String) : ExamUiState
}

class ExamViewModel(
    private val repository: ExamRepository,
    private val studentProfile: UserProfile
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExamUiState>(ExamUiState.Loading)
    val uiState: StateFlow<ExamUiState> = _uiState.asStateFlow()

    private var currentTest: ExamTest? = null
    private var questions: List<ExamQuestion> = emptyList()
    private var answerKey: ExamAnswerKey? = null

    private var activeAttemptId: String = ""
    private var currentQuestionIndex: Int = 0
    private var selectedLanguage: String = "EN"
    private var remainingSeconds: Int = 0

    // Answers and statuses
    private val userAnswers = mutableMapOf<String, String>() // questionId -> "A","B","C","D"
    private val reviewFlags = mutableSetOf<String>() // questionIds marked for review
    private val visitedQuestions = mutableSetOf<String>() // questionIds visited by user

    private var timerJob: Job? = null
    private var autoSaveJob: Job? = null

    fun loadTest(test: ExamTest) {
        currentTest = test
        _uiState.value = ExamUiState.Loading
        viewModelScope.launch {
            try {
                var loadedQuestions = repository.getQuestionsForTest(test.id)
                if (loadedQuestions.isEmpty()) {
                    loadedQuestions = SeedExamData.getQuestionsForTest(test.id).first
                }
                questions = loadedQuestions
                answerKey = repository.getAnswerKeyForTest(test.id) ?: SeedExamData.getQuestionsForTest(test.id).second

                // 1. Strict Single Attempt Check: Has student already submitted this exam?
                val submittedResult = repository.getStudentResultForTest(studentProfile.userId, test.id)
                if (submittedResult != null) {
                    _uiState.value = ExamUiState.ResultReady(submittedResult)
                    return@launch
                }

                // Check for existing active in-progress attempt
                val existingAttempt = repository.getActiveAttempt(test.id)
                if (existingAttempt != null) {
                    activeAttemptId = existingAttempt.id
                    userAnswers.clear()
                    userAnswers.putAll(existingAttempt.answers)
                    reviewFlags.clear()
                    reviewFlags.addAll(existingAttempt.reviewFlags)
                    remainingSeconds = if (existingAttempt.remainingSeconds > 0) {
                        existingAttempt.remainingSeconds
                    } else {
                        test.durationMinutes * 60
                    }
                    startCbtExam(test)
                } else {
                    _uiState.value = ExamUiState.InInstructions(test)
                }
            } catch (e: Exception) {
                val seedData = SeedExamData.getQuestionsForTest(test.id)
                questions = seedData.first
                answerKey = seedData.second
                _uiState.value = ExamUiState.InInstructions(test)
            }
        }
    }

    fun startCbtExam(test: ExamTest) {
        currentTest = test
        if (questions.isEmpty()) {
            val fallback = SeedExamData.getQuestionsForTest(test.id)
            questions = fallback.first
            answerKey = fallback.second
        }

        if (activeAttemptId.isBlank()) {
            activeAttemptId = "att_${test.id}_${studentProfile.userId}_${System.currentTimeMillis()}"
            remainingSeconds = test.durationMinutes * 60
        }

        // Mark the first question as visited
        questions.firstOrNull()?.let { visitedQuestions.add(it.id) }

        _uiState.value = ExamUiState.TakingExam(
            test = test,
            currentQuestionIndex = currentQuestionIndex,
            language = selectedLanguage,
            remainingSeconds = remainingSeconds
        )

        startTimer()
        startAutoSave()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (remainingSeconds > 0) {
                delay(1000L)
                remainingSeconds--
                val state = _uiState.value
                if (state is ExamUiState.TakingExam) {
                    _uiState.value = state.copy(remainingSeconds = remainingSeconds)
                }
                if (remainingSeconds <= 0) {
                    // Time up! Auto-submit
                    submitExam()
                    break
                }
            }
        }
    }

    private fun startAutoSave() {
        autoSaveJob?.cancel()
        autoSaveJob = viewModelScope.launch {
            while (true) {
                delay(15000L) // Auto-save every 15 seconds
                saveCurrentAttemptState()
            }
        }
    }

    private fun saveCurrentAttemptState() {
        val test = currentTest ?: return
        viewModelScope.launch {
            val attempt = ExamAttempt(
                id = activeAttemptId,
                testId = test.id,
                studentUid = studentProfile.userId,
                studentName = studentProfile.name,
                studentId = studentProfile.studentId,
                status = "in_progress",
                answers = userAnswers.toMap(),
                reviewFlags = reviewFlags.toList(),
                remainingSeconds = remainingSeconds
            )
            repository.saveAttempt(attempt)
        }
    }

    // -------------------------------------------------------------
    // CBT Navigation & Actions
    // -------------------------------------------------------------
    fun selectAnswer(optionLetter: String) {
        val q = questions.getOrNull(currentQuestionIndex) ?: return
        userAnswers[q.id] = optionLetter
        updateTakingExamState()
    }

    fun clearResponse() {
        val q = questions.getOrNull(currentQuestionIndex) ?: return
        userAnswers.remove(q.id)
        updateTakingExamState()
    }

    fun markForReviewAndNext() {
        val q = questions.getOrNull(currentQuestionIndex) ?: return
        reviewFlags.add(q.id)
        navigateToNextQuestion()
    }

    fun saveAndNext() {
        val q = questions.getOrNull(currentQuestionIndex) ?: return
        // If it was previously marked for review only without an answer, keep it or remove
        navigateToNextQuestion()
    }

    fun navigateToPrevious() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
            questions.getOrNull(currentQuestionIndex)?.let { visitedQuestions.add(it.id) }
            updateTakingExamState()
        }
    }

    fun navigateToQuestion(index: Int) {
        if (index in questions.indices) {
            currentQuestionIndex = index
            questions.getOrNull(currentQuestionIndex)?.let { visitedQuestions.add(it.id) }
            updateTakingExamState()
        }
    }

    private fun navigateToNextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            questions.getOrNull(currentQuestionIndex)?.let { visitedQuestions.add(it.id) }
        }
        updateTakingExamState()
    }

    fun toggleLanguage() {
        selectedLanguage = if (selectedLanguage == "EN") "HI" else "EN"
        updateTakingExamState()
    }

    fun togglePalette(isOpen: Boolean) {
        val state = _uiState.value
        if (state is ExamUiState.TakingExam) {
            _uiState.value = state.copy(isPaletteOpen = isOpen)
        }
    }

    private fun updateTakingExamState() {
        val test = currentTest ?: return
        _uiState.value = ExamUiState.TakingExam(
            test = test,
            currentQuestionIndex = currentQuestionIndex,
            language = selectedLanguage,
            remainingSeconds = remainingSeconds,
            isPaletteOpen = (_uiState.value as? ExamUiState.TakingExam)?.isPaletteOpen ?: false
        )
    }

    // -------------------------------------------------------------
    // Question Status Calculation for CBT Palette
    // -------------------------------------------------------------
    fun getQuestionStatus(questionId: String): CbtStatus {
        val isAnswered = userAnswers.containsKey(questionId)
        val isMarkedForReview = reviewFlags.contains(questionId)
        val isVisited = visitedQuestions.contains(questionId)

        return when {
            isAnswered && isMarkedForReview -> CbtStatus.ANSWERED_AND_MARKED_FOR_REVIEW
            isMarkedForReview -> CbtStatus.MARKED_FOR_REVIEW
            isAnswered -> CbtStatus.ANSWERED
            isVisited -> CbtStatus.NOT_ANSWERED
            else -> CbtStatus.NOT_VISITED
        }
    }

    fun getPaletteStats(): Map<CbtStatus, Int> {
        val stats = mutableMapOf<CbtStatus, Int>()
        CbtStatus.values().forEach { stats[it] = 0 }
        questions.forEach { q ->
            val status = getQuestionStatus(q.id)
            stats[status] = (stats[status] ?: 0) + 1
        }
        return stats
    }

    fun getQuestionsList(): List<ExamQuestion> = questions

    fun getSelectedAnswer(questionId: String): String? = userAnswers[questionId]

    // -------------------------------------------------------------
    // Submission
    // -------------------------------------------------------------
    fun submitExam() {
        timerJob?.cancel()
        autoSaveJob?.cancel()

        val test = currentTest ?: return
        val key = answerKey ?: ExamAnswerKey(testId = test.id)

        viewModelScope.launch {
            _uiState.value = ExamUiState.Loading
            try {
                val attempt = ExamAttempt(
                    id = activeAttemptId,
                    testId = test.id,
                    studentUid = studentProfile.userId,
                    studentName = studentProfile.name,
                    studentId = studentProfile.studentId,
                    answers = userAnswers.toMap(),
                    reviewFlags = reviewFlags.toList(),
                    remainingSeconds = remainingSeconds
                )
                val result = repository.submitExam(
                    test = test,
                    attempt = attempt,
                    answerKey = key,
                    questions = questions,
                    studentProfile = studentProfile
                )
                _uiState.value = ExamUiState.ResultReady(result)
            } catch (e: Exception) {
                _uiState.value = ExamUiState.Error(e.localizedMessage ?: "Failed to submit exam")
            }
        }
    }
}
