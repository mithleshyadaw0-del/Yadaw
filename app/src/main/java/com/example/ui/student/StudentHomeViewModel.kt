package com.example.ui.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ExamAttempt
import com.example.data.model.ExamResult
import com.example.data.model.ExamSectionInfo
import com.example.data.model.ExamTest
import com.example.data.model.StudyNoteChapter
import com.example.data.model.VideoRecommendation
import com.example.data.repository.ExamRepository
import com.example.data.seed.SeedExamData
import com.example.data.seed.StudyNotesSeedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ExamDashboardTab(val label: String) {
    MOCK_TESTS("MOCK TESTS"),
    PYQS("PYQs"),
    STUDY_NOTES("STUDY NOTES")
}

enum class ExamSubView {
    HOME,
    CLASS_SELECTION,
    EXAM_DASHBOARD,
    PYQ_MARATHON_LIST,
    MOST_SAVED_QUESTIONS,
    FULL_LENGTH_LIST,
    CHAPTER_SUBJECT_LIST,
    CHAPTER_LIST,
    CHAPTER_TEST_LIST,
    SUBJECT_WISE_SUBJECT_LIST,
    SUBJECT_WISE_TEST_LIST,
    SECTIONAL_SUBJECT_LIST,
    SECTIONAL_TEST_LIST,
    ADVANCED_TEST_LIST,
    PYQ_TEST_LIST,
    CHAPTER_STUDY_DETAIL,
    EXAM_MY_RESULTS
}

data class ExamNavState(
    val subView: ExamSubView = ExamSubView.HOME,
    val selectedExamId: String? = null,
    val selectedClass: String? = null,
    val selectedDashboardTab: ExamDashboardTab = ExamDashboardTab.MOCK_TESTS,
    val selectedNotesSubject: String? = null,
    val selectedNoteId: String? = null,
    val selectedSubject: String? = null,
    val selectedChapter: String? = null,
    val selectedPyqFilter: String = "All", // "All", "2025", "2024", "Subject-wise", "Marathon"
    val selectedSubTypeFilter: String = "All",
    val searchQuery: String = ""
)

class StudentHomeViewModel(
    private val repository: ExamRepository,
    private val currentUserId: String
) : ViewModel() {

    private val _navState = MutableStateFlow(ExamNavState())
    val navState: StateFlow<ExamNavState> = _navState.asStateFlow()

    private val _activeAttempt = MutableStateFlow<ExamAttempt?>(null)
    val activeAttempt: StateFlow<ExamAttempt?> = _activeAttempt.asStateFlow()

    val examSections: List<ExamSectionInfo> = SeedExamData.getExamSections()

    val tests: StateFlow<List<ExamTest>> = repository.observeTests()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SeedExamData.getAllTests())

    val results: StateFlow<List<ExamResult>> = repository.observeStudentResults(currentUserId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedQuestionIds: StateFlow<Set<String>> = repository.savedQuestionIds

    val recommendedVideos: List<VideoRecommendation> = SeedExamData.getRecommendedVideos()

    init {
        checkActiveAttempt()
        if (repository.isFirebaseTeacherAdmin()) {
            viewModelScope.launch {
                repository.seedInitialDataIfNeeded()
            }
        }
    }

    // -------------------------------------------------------------
    // Navigation Actions
    // -------------------------------------------------------------
    fun openSection(examId: String) {
        val section = examSections.find { it.id.equals(examId, ignoreCase = true) }
        val availableClasses = getAvailableClassesForExam(examId)
        if (section?.supportsClassSelection == true && availableClasses.isNotEmpty()) {
            _navState.value = ExamNavState(
                subView = ExamSubView.CLASS_SELECTION,
                selectedExamId = examId,
                selectedClass = null
            )
        } else {
            val defaultNotesSubj = getNotesSubjectsForContext(examId, null).firstOrNull()
            _navState.value = ExamNavState(
                subView = ExamSubView.EXAM_DASHBOARD,
                selectedExamId = examId,
                selectedClass = null,
                selectedDashboardTab = ExamDashboardTab.MOCK_TESTS,
                selectedNotesSubject = defaultNotesSubj
            )
        }
    }

    fun selectClass(examClass: String) {
        val cur = _navState.value
        val defaultNotesSubj = getNotesSubjectsForContext(cur.selectedExamId, examClass).firstOrNull()
        _navState.value = cur.copy(
            subView = ExamSubView.EXAM_DASHBOARD,
            selectedClass = examClass,
            selectedDashboardTab = ExamDashboardTab.MOCK_TESTS,
            selectedNotesSubject = defaultNotesSubj,
            selectedSubject = null,
            selectedChapter = null,
            selectedSubTypeFilter = "All",
            searchQuery = ""
        )
    }

    fun changeClass() {
        val cur = _navState.value
        val examId = cur.selectedExamId ?: return
        val availableClasses = getAvailableClassesForExam(examId)
        if (availableClasses.isNotEmpty()) {
            _navState.value = cur.copy(
                subView = ExamSubView.CLASS_SELECTION,
                selectedSubject = null,
                selectedChapter = null,
                searchQuery = ""
            )
        } else {
            resetToHome()
        }
    }

    fun selectDashboardTab(tab: ExamDashboardTab) {
        val cur = _navState.value
        val notesSubject = cur.selectedNotesSubject
            ?: getNotesSubjectsForContext(cur.selectedExamId, cur.selectedClass).firstOrNull()
        _navState.value = cur.copy(
            selectedDashboardTab = tab,
            selectedNotesSubject = notesSubject
        )
    }

    fun selectNotesSubject(subject: String) {
        _navState.value = _navState.value.copy(selectedNotesSubject = subject)
    }

    fun openNoteDetail(noteId: String) {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.CHAPTER_STUDY_DETAIL,
            selectedNoteId = noteId
        )
    }

    fun openPyqMarathon() {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.PYQ_MARATHON_LIST,
            searchQuery = ""
        )
    }

    fun openMostSavedQuestions() {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.MOST_SAVED_QUESTIONS,
            searchQuery = ""
        )
    }

    fun openFullLength() {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.FULL_LENGTH_LIST,
            selectedSubTypeFilter = "All",
            searchQuery = ""
        )
    }

    fun openAdvancedTests() {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.ADVANCED_TEST_LIST,
            searchQuery = ""
        )
    }

    fun openPyqTestList(filter: String = "All") {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.PYQ_TEST_LIST,
            selectedPyqFilter = filter,
            searchQuery = ""
        )
    }

    fun selectPyqFilter(filter: String) {
        _navState.value = _navState.value.copy(selectedPyqFilter = filter)
    }

    fun selectSubTypeFilter(subType: String) {
        _navState.value = _navState.value.copy(selectedSubTypeFilter = subType)
    }

    fun openChapterWise() {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.CHAPTER_SUBJECT_LIST,
            selectedSubject = null,
            selectedChapter = null,
            searchQuery = ""
        )
    }

    fun selectSubjectForChapter(subject: String) {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.CHAPTER_LIST,
            selectedSubject = subject,
            selectedChapter = null,
            searchQuery = ""
        )
    }

    fun selectChapterForTests(chapter: String) {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.CHAPTER_TEST_LIST,
            selectedChapter = chapter,
            searchQuery = ""
        )
    }

    fun openSubjectWise() {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.SUBJECT_WISE_SUBJECT_LIST,
            selectedSubject = null,
            searchQuery = ""
        )
    }

    fun selectSubjectForSubjectWise(subject: String) {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.SUBJECT_WISE_TEST_LIST,
            selectedSubject = subject,
            searchQuery = ""
        )
    }

    fun openSectional() {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.SECTIONAL_SUBJECT_LIST,
            selectedSubject = null,
            searchQuery = ""
        )
    }

    fun selectSubjectForSectional(subject: String) {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.SECTIONAL_TEST_LIST,
            selectedSubject = subject,
            searchQuery = ""
        )
    }

    fun openMyResults() {
        _navState.value = _navState.value.copy(
            subView = ExamSubView.EXAM_MY_RESULTS,
            searchQuery = ""
        )
    }

    fun updateSearchQuery(query: String) {
        _navState.value = _navState.value.copy(searchQuery = query)
    }

    fun toggleSaveQuestion(questionId: String) {
        repository.toggleSaveQuestion(questionId)
    }

    fun navigateBack(): Boolean {
        val cur = _navState.value
        val next = when (cur.subView) {
            ExamSubView.HOME -> return false
            ExamSubView.CLASS_SELECTION -> ExamNavState(subView = ExamSubView.HOME)
            ExamSubView.EXAM_DASHBOARD -> {
                val examId = cur.selectedExamId
                val section = examSections.find { it.id == examId }
                if (section?.supportsClassSelection == true && getAvailableClassesForExam(examId ?: "").isNotEmpty()) {
                    cur.copy(subView = ExamSubView.CLASS_SELECTION, selectedSubject = null, selectedChapter = null, searchQuery = "")
                } else {
                    ExamNavState(subView = ExamSubView.HOME)
                }
            }
            ExamSubView.PYQ_MARATHON_LIST,
            ExamSubView.MOST_SAVED_QUESTIONS,
            ExamSubView.FULL_LENGTH_LIST,
            ExamSubView.ADVANCED_TEST_LIST,
            ExamSubView.PYQ_TEST_LIST,
            ExamSubView.CHAPTER_SUBJECT_LIST,
            ExamSubView.SUBJECT_WISE_SUBJECT_LIST,
            ExamSubView.SECTIONAL_SUBJECT_LIST,
            ExamSubView.CHAPTER_STUDY_DETAIL,
            ExamSubView.EXAM_MY_RESULTS -> cur.copy(
                subView = ExamSubView.EXAM_DASHBOARD,
                selectedSubject = null,
                selectedChapter = null,
                selectedNoteId = null,
                searchQuery = ""
            )
            ExamSubView.CHAPTER_LIST -> cur.copy(
                subView = ExamSubView.CHAPTER_SUBJECT_LIST,
                selectedSubject = null,
                selectedChapter = null,
                searchQuery = ""
            )
            ExamSubView.CHAPTER_TEST_LIST -> cur.copy(
                subView = ExamSubView.CHAPTER_LIST,
                selectedChapter = null,
                searchQuery = ""
            )
            ExamSubView.SUBJECT_WISE_TEST_LIST -> cur.copy(
                subView = ExamSubView.SUBJECT_WISE_SUBJECT_LIST,
                selectedSubject = null,
                searchQuery = ""
            )
            ExamSubView.SECTIONAL_TEST_LIST -> cur.copy(
                subView = ExamSubView.SECTIONAL_SUBJECT_LIST,
                selectedSubject = null,
                searchQuery = ""
            )
        }
        _navState.value = next
        return true
    }

    fun resetToHome() {
        _navState.value = ExamNavState(subView = ExamSubView.HOME)
    }

    // -------------------------------------------------------------
    // Strictly Filtered Query Helpers (Zero Mixing Across Exams/Classes)
    // -------------------------------------------------------------
    fun getSectionInfo(examId: String?): ExamSectionInfo? {
        return examSections.find { it.id.equals(examId, ignoreCase = true) }
    }

    fun getAvailableClassesForExam(examId: String): List<String> {
        val publishedForExam = tests.value.filter {
            it.isPublished && it.category.equals(examId, ignoreCase = true) && it.examClass.isNotBlank()
        }
        val dbClasses = publishedForExam.map { it.examClass }.distinct()
        val combined = when {
            examId.equals("Navodaya", ignoreCase = true) ||
            examId.equals("Sainik School", ignoreCase = true) -> {
                (listOf("Class 6", "Class 9") + dbClasses).distinct()
            }
            else -> dbClasses // Simultala & Olympiad strictly reflect DB/syllabus without inventing classes
        }
        return combined.sorted()
    }

    fun getTestsForContext(
        examId: String? = _navState.value.selectedExamId,
        examClass: String? = _navState.value.selectedClass,
        testType: String? = null,
        subject: String? = null
    ): List<ExamTest> {
        return tests.value.filter { test ->
            if (!test.isPublished) return@filter false
            val matchesExam = examId == null || examId == "All" || test.category.equals(examId, ignoreCase = true)
            val matchesClass = examClass.isNullOrBlank() || test.examClass.equals(examClass, ignoreCase = true)
            val resolvedType = test.testType.ifBlank { "FULL_LENGTH" }
            val matchesType = testType.isNullOrBlank() || resolvedType.equals(testType, ignoreCase = true)
            val matchesSubject = subject.isNullOrBlank() || test.subject.equals(subject, ignoreCase = true)
            matchesExam && matchesClass && matchesType && matchesSubject
        }
    }

    fun getPyqTestsForContext(
        examId: String? = _navState.value.selectedExamId,
        examClass: String? = _navState.value.selectedClass,
        filter: String = "All"
    ): List<ExamTest> {
        val allPyqs = getTestsForContext(examId, examClass).filter { test ->
            test.testType.equals("PYQ", true) ||
                test.subType.equals("Previous Year", true) ||
                test.subType.equals("Marathon", true)
        }
        return when {
            filter.equals("All", true) -> allPyqs
            filter.equals("Marathon", true) -> allPyqs.filter { it.subType.equals("Marathon", true) }
            filter.equals("Subject-wise", true) -> allPyqs.filter { !it.subject.equals("All Subjects", true) }
            filter.equals("Mock Sets", true) -> allPyqs.filter { it.testType.equals("PYQ", true) || it.subType.equals("Previous Year", true) }
            filter.all { it.isDigit() } -> allPyqs.filter { it.examYear == filter }
            else -> allPyqs
        }
    }

    fun getMarathonTestsForContext(
        examId: String? = _navState.value.selectedExamId,
        examClass: String? = _navState.value.selectedClass
    ): List<ExamTest> {
        val marathons = getTestsForContext(examId, examClass).filter {
            it.subType.equals("Marathon", true) || it.testType.equals("PYQ", true)
        }
        return if (marathons.isNotEmpty()) marathons else getPyqTestsForContext(examId, examClass)
    }

    fun getSubjectsForContext(
        examId: String? = _navState.value.selectedExamId,
        examClass: String? = _navState.value.selectedClass,
        testType: String
    ): List<String> {
        val priorityOrder = listOf(
            "Mathematics",
            "Reasoning / Mental Ability",
            "Science",
            "English",
            "Hindi / Language",
            "General Knowledge"
        )
        val rawSubjects = getTestsForContext(examId, examClass, testType)
            .map { it.subject }
            .filter { it.isNotBlank() && !it.equals("All Subjects", ignoreCase = true) }
            .distinct()

        return rawSubjects.sortedBy { subj ->
            val idx = priorityOrder.indexOfFirst { it.equals(subj, ignoreCase = true) }
            if (idx >= 0) idx else Int.MAX_VALUE
        }
    }

    fun getNotesSubjectsForContext(
        examId: String? = _navState.value.selectedExamId,
        examClass: String? = _navState.value.selectedClass
    ): List<String> = StudyNotesSeedData.getSubjectsForNotes(examId, examClass)

    fun getStudyNotesForContext(
        examId: String? = _navState.value.selectedExamId,
        examClass: String? = _navState.value.selectedClass,
        subject: String? = null
    ): List<StudyNoteChapter> = StudyNotesSeedData.getNotesForContext(examId, examClass, subject)

    fun getNoteById(noteId: String?): StudyNoteChapter? {
        if (noteId.isNullOrBlank()) return null
        return StudyNotesSeedData.getAllNotes().find { it.id == noteId }
    }

    fun getSavedAndHighYieldQuestions(
        examId: String? = _navState.value.selectedExamId,
        examClass: String? = _navState.value.selectedClass
    ): List<ExamRepository.ContextQuestionItem> =
        repository.getHighYieldQuestionsForContext(examId, examClass)

    fun getResultsForContext(
        examId: String? = _navState.value.selectedExamId,
        examClass: String? = _navState.value.selectedClass
    ): List<ExamResult> {
        val allTestsMap = tests.value.associateBy { it.id }
        return results.value.filter { res ->
            val testMeta = allTestsMap[res.testId]
            val cat = res.examCategory.ifBlank { testMeta?.category ?: "" }
            val cls = res.examClass.ifBlank { testMeta?.examClass ?: "" }
            val matchesExam = examId == null || examId == "All" || cat.equals(examId, ignoreCase = true)
            val matchesClass = examClass.isNullOrBlank() || cls.equals(examClass, ignoreCase = true)
            matchesExam && matchesClass
        }
    }

    fun getResultForTest(testId: String): ExamResult? {
        return results.value.firstOrNull { it.testId == testId }
    }

    fun hasAttemptedTest(testId: String): Boolean {
        return results.value.any { it.testId == testId }
    }

    fun checkActiveAttempt() {
        viewModelScope.launch {
            val attempt = repository.getActiveAttempt("test_railway_math_01")
            _activeAttempt.value = attempt
        }
    }
}
