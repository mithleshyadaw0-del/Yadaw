package com.example.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamResult
import com.example.data.model.ExamTest
import com.example.data.repository.ExamRepository
import com.example.data.seed.SeedExamData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface AdminOpState {
    object Idle : AdminOpState
    object Loading : AdminOpState
    data class Success(val message: String) : AdminOpState
    data class Error(val message: String) : AdminOpState
}

class AdminViewModel(private val repository: ExamRepository) : ViewModel() {

    private val _opState = MutableStateFlow<AdminOpState>(AdminOpState.Idle)
    val opState: StateFlow<AdminOpState> = _opState.asStateFlow()

    val tests: StateFlow<List<ExamTest>> = repository.observeTests()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allResults: StateFlow<List<ExamResult>> = repository.observeAllResults()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun clearOpState() {
        _opState.value = AdminOpState.Idle
    }

    fun createNewTest(test: ExamTest) {
        viewModelScope.launch {
            _opState.value = AdminOpState.Loading
            try {
                repository.createTest(test)
                _opState.value = AdminOpState.Success("नया टेस्ट '${test.title}' सफलतापूर्वक बनाया गया!")
            } catch (e: Exception) {
                _opState.value = AdminOpState.Error(e.localizedMessage ?: "Failed to create test")
            }
        }
    }

    fun togglePublish(testId: String, currentStatus: Boolean) {
        viewModelScope.launch {
            try {
                repository.updateTestPublishStatus(testId, !currentStatus)
                _opState.value = AdminOpState.Success(
                    if (!currentStatus) "टेस्ट प्रकाशित (Published) कर दिया गया है!" else "टेस्ट अप्रकाशित (Unpublished) किया गया।"
                )
            } catch (e: Exception) {
                _opState.value = AdminOpState.Error(e.localizedMessage ?: "Failed to update test")
            }
        }
    }

    fun deleteTest(testId: String) {
        viewModelScope.launch {
            _opState.value = AdminOpState.Loading
            try {
                repository.deleteTest(testId)
                _opState.value = AdminOpState.Success("टेस्ट सफलतापूर्वक हटा दिया गया!")
            } catch (e: Exception) {
                _opState.value = AdminOpState.Error(e.localizedMessage ?: "Failed to delete test")
            }
        }
    }

    fun addSingleQuestion(
        testId: String,
        qEn: String,
        qHi: String,
        opA: String,
        opB: String,
        opC: String,
        opD: String,
        correctOption: String,
        expEn: String,
        expHi: String,
        marks: Double = 1.0
    ) {
        viewModelScope.launch {
            _opState.value = AdminOpState.Loading
            try {
                val existingQuestions = repository.getQuestionsForTest(testId).toMutableList()
                val existingKey = repository.getAnswerKeyForTest(testId) ?: ExamAnswerKey(testId = testId)

                val newIndex = existingQuestions.size
                val qId = "${testId}_q${System.currentTimeMillis()}"

                val newQuestion = ExamQuestion(
                    id = qId,
                    testId = testId,
                    questionIndex = newIndex,
                    questionEn = qEn,
                    questionHi = qHi.ifBlank { qEn },
                    optionsEn = listOf("A) $opA", "B) $opB", "C) $opC", "D) $opD"),
                    optionsHi = listOf("A) $opA", "B) $opB", "C) $opC", "D) $opD"),
                    marks = marks
                )

                existingQuestions.add(newQuestion)

                val updatedKeys = existingKey.keys.toMutableMap()
                updatedKeys[qId] = correctOption.uppercase()

                val updatedExpEn = existingKey.explanationsEn.toMutableMap()
                updatedExpEn[qId] = expEn

                val updatedExpHi = existingKey.explanationsHi.toMutableMap()
                updatedExpHi[qId] = expHi.ifBlank { expEn }

                val updatedKey = existingKey.copy(
                    keys = updatedKeys,
                    explanationsEn = updatedExpEn,
                    explanationsHi = updatedExpHi
                )

                repository.saveQuestionsAndKey(testId, existingQuestions, updatedKey)
                _opState.value = AdminOpState.Success("प्रश्न सफलतापूर्वक जोड़ दिया गया! कुल प्रश्न: ${existingQuestions.size}")
            } catch (e: Exception) {
                _opState.value = AdminOpState.Error(e.localizedMessage ?: "Failed to add question")
            }
        }
    }

    fun importCsv(testId: String, csvText: String, append: Boolean = false) {
        viewModelScope.launch {
            _opState.value = AdminOpState.Loading
            try {
                val (success, fail) = repository.importQuestionsFromCsv(testId, csvText, append = append)
                if (success > 0) {
                    val modeText = if (append) "मौजूदा प्रश्नों में जोड़े गए" else "अपलोड किए गए"
                    _opState.value = AdminOpState.Success("सफलतापूर्वक $success प्रश्न $modeText! (छूटे हुए: $fail)")
                } else {
                    _opState.value = AdminOpState.Error("प्रश्नों को पहचाना नहीं जा सका। कृपया एक्सल / CSV प्रारूप जांचें।")
                }
            } catch (e: Exception) {
                _opState.value = AdminOpState.Error(e.localizedMessage ?: "Failed to import questions")
            }
        }
    }

    fun seedStandardTests() {
        viewModelScope.launch {
            _opState.value = AdminOpState.Loading
            try {
                SeedExamData.getStandardTests().forEach { test ->
                    repository.createTest(test)
                }
                val (qList, key) = SeedExamData.getQuestionsForRailway01()
                repository.saveQuestionsAndKey("test_railway_math_01", qList, key)
                _opState.value = AdminOpState.Success("मानक टेस्ट एवं 15+ गणित प्रश्न सफलतापूर्वक लोड हो गए!")
            } catch (e: Exception) {
                _opState.value = AdminOpState.Error(e.localizedMessage ?: "Seeding failed")
            }
        }
    }
}
