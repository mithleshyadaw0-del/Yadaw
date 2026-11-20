package com.example.ui.student

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.ExamResult
import com.example.data.model.ExamSectionInfo
import com.example.data.model.ExamTest
import com.example.data.model.UserProfile
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SurfaceSoft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(
    viewModel: StudentHomeViewModel,
    userProfile: UserProfile?,
    isAdmin: Boolean,
    onStartTest: (ExamTest) -> Unit,
    onViewResult: (ExamResult) -> Unit,
    onViewResults: () -> Unit,
    onViewLeaderboard: (String) -> Unit,
    onViewVideos: () -> Unit,
    onViewAnalysis: () -> Unit,
    onOpenAdmin: () -> Unit,
    onOpenProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tests by viewModel.tests.collectAsState()
    val results by viewModel.results.collectAsState()
    val navState by viewModel.navState.collectAsState()
    val activeAttempt by viewModel.activeAttempt.collectAsState()
    val savedQuestionIds by viewModel.savedQuestionIds.collectAsState()

    if (navState.subView != ExamSubView.HOME) {
        BackHandler {
            viewModel.navigateBack()
        }
    }

    AnimatedContent(
        targetState = navState,
        transitionSpec = {
            fadeIn(animationSpec = tween(220)) togetherWith fadeOut(animationSpec = tween(180))
        },
        label = "ExamNavigationTransition"
    ) { state ->
        when (state.subView) {
            ExamSubView.CLASS_SELECTION -> {
                val examId = state.selectedExamId ?: "Navodaya"
                val classes = viewModel.getAvailableClassesForExam(examId)
                ClassSelectionScreen(
                    examId = examId,
                    availableClasses = classes,
                    tests = tests,
                    results = results,
                    onSelectClass = { cls -> viewModel.selectClass(cls) },
                    onBack = { viewModel.navigateBack() }
                )
            }

            ExamSubView.EXAM_DASHBOARD -> {
                val examId = state.selectedExamId ?: "All"
                val sectionInfo = viewModel.getSectionInfo(examId)
                val availableClasses = viewModel.getAvailableClassesForExam(examId)
                val examTests = viewModel.getTestsForContext(examId, state.selectedClass)
                val pyqTests = viewModel.getPyqTestsForContext(examId, state.selectedClass)
                val marathonTests = viewModel.getMarathonTestsForContext(examId, state.selectedClass)
                val savedItems = viewModel.getSavedAndHighYieldQuestions(examId, state.selectedClass)
                val examResults = viewModel.getResultsForContext(examId, state.selectedClass)
                val noteSubjects = viewModel.getNotesSubjectsForContext(examId, state.selectedClass)
                val activeNoteSubject = state.selectedNotesSubject
                    ?.takeIf { s -> noteSubjects.any { it.equals(s, true) } }
                    ?: noteSubjects.firstOrNull()
                val notesForSubject = viewModel.getStudyNotesForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    subject = activeNoteSubject
                )

                TestbookExamDashboardScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    selectedTab = state.selectedDashboardTab,
                    selectedNotesSubject = activeNoteSubject,
                    allExamTests = examTests,
                    pyqTests = pyqTests,
                    marathonTests = marathonTests,
                    savedQuestionsCount = savedItems.size,
                    examResults = examResults,
                    noteSubjects = noteSubjects,
                    notesForSelectedSubject = notesForSubject,
                    supportsClassChange = sectionInfo?.supportsClassSelection == true && availableClasses.isNotEmpty(),
                    onChangeClass = { viewModel.changeClass() },
                    onBack = { viewModel.navigateBack() },
                    onSelectTab = { viewModel.selectDashboardTab(it) },
                    onSelectNotesSubject = { viewModel.selectNotesSubject(it) },
                    onOpenNoteDetail = { viewModel.openNoteDetail(it) },
                    onOpenPyqMarathon = { viewModel.openPyqMarathon() },
                    onOpenMostSavedQuestions = { viewModel.openMostSavedQuestions() },
                    onOpenChapterWise = { viewModel.openChapterWise() },
                    onOpenSubjectWise = { viewModel.openSubjectWise() },
                    onOpenSectional = { viewModel.openSectional() },
                    onOpenFullLength = { viewModel.openFullLength() },
                    onOpenAdvanced = { viewModel.openAdvancedTests() },
                    onOpenPyqList = { filter -> viewModel.openPyqTestList(filter) },
                    onOpenMyResults = { viewModel.openMyResults() }
                )
            }

            ExamSubView.PYQ_MARATHON_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val marathonTests = viewModel.getMarathonTestsForContext(examId, state.selectedClass)
                CategoryTestsListScreen(
                    screenTitle = "PYQ Marathon Live Tests",
                    examId = examId,
                    selectedClass = state.selectedClass,
                    tests = marathonTests,
                    results = results,
                    searchQuery = state.searchQuery,
                    onSearchChange = { viewModel.updateSearchQuery(it) },
                    onBack = { viewModel.navigateBack() },
                    onStartTest = onStartTest,
                    onViewResult = onViewResult,
                    onViewLeaderboard = onViewLeaderboard
                )
            }

            ExamSubView.MOST_SAVED_QUESTIONS -> {
                val examId = state.selectedExamId ?: "All"
                val itemsList = viewModel.getSavedAndHighYieldQuestions(examId, state.selectedClass)
                MostSavedQuestionsScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    itemsList = itemsList,
                    savedQuestionIds = savedQuestionIds,
                    onToggleSave = { viewModel.toggleSaveQuestion(it) },
                    onBack = { viewModel.navigateBack() }
                )
            }

            ExamSubView.FULL_LENGTH_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val fullLengthTests = viewModel.getTestsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "FULL_LENGTH"
                )
                FullLengthTestsScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    tests = fullLengthTests,
                    results = results,
                    subTypeFilter = state.selectedSubTypeFilter,
                    searchQuery = state.searchQuery,
                    onSubTypeSelect = { viewModel.selectSubTypeFilter(it) },
                    onSearchChange = { viewModel.updateSearchQuery(it) },
                    onBack = { viewModel.navigateBack() },
                    onStartTest = onStartTest,
                    onViewResult = onViewResult,
                    onViewLeaderboard = onViewLeaderboard
                )
            }

            ExamSubView.ADVANCED_TEST_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val advTests = viewModel.getTestsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "ADVANCED"
                )
                CategoryTestsListScreen(
                    screenTitle = "Advanced Rank Booster Tests",
                    examId = examId,
                    selectedClass = state.selectedClass,
                    tests = advTests,
                    results = results,
                    searchQuery = state.searchQuery,
                    onSearchChange = { viewModel.updateSearchQuery(it) },
                    onBack = { viewModel.navigateBack() },
                    onStartTest = onStartTest,
                    onViewResult = onViewResult,
                    onViewLeaderboard = onViewLeaderboard
                )
            }

            ExamSubView.PYQ_TEST_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val allPyqs = viewModel.getPyqTestsForContext(examId, state.selectedClass, "All")
                val years = allPyqs.mapNotNull { it.examYear.takeIf { y -> y.isNotBlank() } }.distinct().sortedDescending()
                val filterChips = listOf("All") + years + listOf("Subject-wise", "Marathon")
                val filteredPyqs = viewModel.getPyqTestsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    filter = state.selectedPyqFilter
                )
                CategoryTestsListScreen(
                    screenTitle = "Previous Year Papers (PYQs)",
                    examId = examId,
                    selectedClass = state.selectedClass,
                    tests = filteredPyqs,
                    results = results,
                    searchQuery = state.searchQuery,
                    filterChips = filterChips,
                    activeFilter = state.selectedPyqFilter,
                    onFilterSelect = { viewModel.selectPyqFilter(it) },
                    onSearchChange = { viewModel.updateSearchQuery(it) },
                    onBack = { viewModel.navigateBack() },
                    onStartTest = onStartTest,
                    onViewResult = onViewResult,
                    onViewLeaderboard = onViewLeaderboard
                )
            }

            ExamSubView.CHAPTER_SUBJECT_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val subjects = viewModel.getSubjectsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "CHAPTER_WISE"
                )
                val allChapterTests = viewModel.getTestsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "CHAPTER_WISE"
                )
                SubjectSelectionScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    modeTitle = "Chapter Wise Tests",
                    subjects = subjects,
                    allTestsForType = allChapterTests,
                    results = results,
                    onSelectSubject = { subj -> viewModel.selectSubjectForChapter(subj) },
                    onBack = { viewModel.navigateBack() }
                )
            }

            ExamSubView.CHAPTER_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val subject = state.selectedSubject ?: "Mathematics"
                val chapterTests = viewModel.getTestsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "CHAPTER_WISE",
                    subject = subject
                )
                ChapterListScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    subject = subject,
                    chapterTests = chapterTests,
                    results = results,
                    searchQuery = state.searchQuery,
                    onSearchChange = { viewModel.updateSearchQuery(it) },
                    onSelectChapter = { chap -> viewModel.selectChapterForTests(chap) },
                    onBack = { viewModel.navigateBack() }
                )
            }

            ExamSubView.CHAPTER_TEST_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val subject = state.selectedSubject ?: "Mathematics"
                val chapterName = state.selectedChapter ?: ""
                val chapterTests = viewModel.getTestsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "CHAPTER_WISE",
                    subject = subject
                ).filter { it.chapter.ifBlank { it.title }.equals(chapterName, ignoreCase = true) }

                ChapterTestListScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    subject = subject,
                    chapterName = chapterName,
                    chapterTests = chapterTests,
                    results = results,
                    onBack = { viewModel.navigateBack() },
                    onStartTest = onStartTest,
                    onViewResult = onViewResult,
                    onViewLeaderboard = onViewLeaderboard
                )
            }

            ExamSubView.SUBJECT_WISE_SUBJECT_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val subjects = viewModel.getSubjectsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "SUBJECT_WISE"
                )
                val allSubjectTests = viewModel.getTestsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "SUBJECT_WISE"
                )
                SubjectSelectionScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    modeTitle = "Subject Wise Tests",
                    subjects = subjects,
                    allTestsForType = allSubjectTests,
                    results = results,
                    onSelectSubject = { subj -> viewModel.selectSubjectForSubjectWise(subj) },
                    onBack = { viewModel.navigateBack() }
                )
            }

            ExamSubView.SUBJECT_WISE_TEST_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val subject = state.selectedSubject ?: "Mathematics"
                val subjectTests = viewModel.getTestsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "SUBJECT_WISE",
                    subject = subject
                )
                SubjectTestsListScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    subject = subject,
                    modeLabel = "Subject Wise",
                    subjectTests = subjectTests,
                    results = results,
                    onBack = { viewModel.navigateBack() },
                    onStartTest = onStartTest,
                    onViewResult = onViewResult,
                    onViewLeaderboard = onViewLeaderboard
                )
            }

            ExamSubView.SECTIONAL_SUBJECT_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val subjects = viewModel.getSubjectsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "SECTIONAL"
                )
                val allSectionalTests = viewModel.getTestsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "SECTIONAL"
                )
                SubjectSelectionScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    modeTitle = "Sectional Tests",
                    subjects = subjects,
                    allTestsForType = allSectionalTests,
                    results = results,
                    onSelectSubject = { subj -> viewModel.selectSubjectForSectional(subj) },
                    onBack = { viewModel.navigateBack() }
                )
            }

            ExamSubView.SECTIONAL_TEST_LIST -> {
                val examId = state.selectedExamId ?: "All"
                val subject = state.selectedSubject ?: "Mathematics"
                val sectionalTests = viewModel.getTestsForContext(
                    examId = examId,
                    examClass = state.selectedClass,
                    testType = "SECTIONAL",
                    subject = subject
                )
                SubjectTestsListScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    subject = subject,
                    modeLabel = "Sectional",
                    subjectTests = sectionalTests,
                    results = results,
                    onBack = { viewModel.navigateBack() },
                    onStartTest = onStartTest,
                    onViewResult = onViewResult,
                    onViewLeaderboard = onViewLeaderboard
                )
            }

            ExamSubView.CHAPTER_STUDY_DETAIL -> {
                val note = viewModel.getNoteById(state.selectedNoteId)
                if (note == null) {
                    LaunchedEffect(state.selectedNoteId) {
                        viewModel.navigateBack()
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DarkExamColors.Bg)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DarkEmptyTestsCard("अध्ययन नोट्स लोड हो रहे हैं...")
                    }
                } else {
                    val linkedTests = tests.filter { it.isPublished && it.id in note.linkedTestIds }
                    ChapterStudyNotesDetailScreen(
                        note = note,
                        linkedTests = linkedTests,
                        results = results,
                        onBack = { viewModel.navigateBack() },
                        onStartTest = onStartTest,
                        onViewResult = onViewResult,
                        onViewLeaderboard = onViewLeaderboard
                    )
                }
            }

            ExamSubView.EXAM_MY_RESULTS -> {
                val examId = state.selectedExamId ?: "All"
                val examResults = viewModel.getResultsForContext(examId, state.selectedClass)
                ExamMyResultsScreen(
                    examId = examId,
                    selectedClass = state.selectedClass,
                    examResults = examResults,
                    onBack = { viewModel.navigateBack() },
                    onViewResult = onViewResult
                )
            }

            ExamSubView.HOME -> {
                MainHomeDashboard(
                    sections = viewModel.examSections,
                    tests = tests,
                    results = results,
                    userProfile = userProfile,
                    isAdmin = isAdmin,
                    activeAttemptTestId = activeAttempt?.testId,
                    onOpenSection = { examId -> viewModel.openSection(examId) },
                    onStartTest = onStartTest,
                    onViewResults = onViewResults,
                    onViewLeaderboard = onViewLeaderboard,
                    onViewVideos = onViewVideos,
                    onViewAnalysis = onViewAnalysis,
                    onOpenAdmin = onOpenAdmin,
                    onOpenProfile = onOpenProfile,
                    modifier = modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainHomeDashboard(
    sections: List<ExamSectionInfo>,
    tests: List<ExamTest>,
    results: List<ExamResult>,
    userProfile: UserProfile?,
    isAdmin: Boolean,
    activeAttemptTestId: String?,
    onOpenSection: (String) -> Unit,
    onStartTest: (ExamTest) -> Unit,
    onViewResults: () -> Unit,
    onViewLeaderboard: (String) -> Unit,
    onViewVideos: () -> Unit,
    onViewAnalysis: () -> Unit,
    onOpenAdmin: () -> Unit,
    onOpenProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mithlesh_sir_photo),
                            contentDescription = "Mithlesh Sir",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, Color(0xFFFDE047), CircleShape)
                        )
                        Column {
                            Text(
                                text = "Maths By Mithlesh Sir",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "स्वागत है, ${userProfile?.name?.ifBlank { "विद्यार्थी" } ?: "विद्यार्थी"} (${userProfile?.studentId ?: "ID"})",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFFDE047),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                actions = {
                    if (isAdmin) {
                        IconButton(
                            onClick = onOpenAdmin,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("teacher_admin_dashboard_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = "Teacher Admin Dashboard",
                                tint = AccentOrange
                            )
                        }
                    }
                    IconButton(
                        onClick = onOpenProfile,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("student_profile_button")
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.25f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = (userProfile?.name?.take(1)?.uppercase() ?: "S"),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue)
            )
        },
        containerColor = SurfaceSoft,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Hero Welcome Banner
            item {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF1E3A8A), Color(0xFF0F172A))
                                )
                            )
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFFFDE047).copy(alpha = 0.2f),
                                    modifier = Modifier.padding(bottom = 6.dp)
                                ) {
                                    Text(
                                        text = "★ ऑनलाइन परीक्षा केंद्र ★",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFFFDE047),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                Text(
                                    text = "मिथलेश सर स्पेशल मैथ्स",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "प्रत्येक परीक्षा का अलग डैशबोर्ड, कक्षा-वार मॉक टेस्ट, अध्याय व सेक्शनल अभ्यास उपलब्ध है।",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFE2E8F0)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Image(
                                painter = painterResource(id = R.drawable.mithlesh_sir_photo),
                                contentDescription = "Mithlesh Sir Photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(76.dp)
                                    .clip(CircleShape)
                                    .border(2.5.dp, Color(0xFFFDE047), CircleShape)
                            )
                        }
                    }
                }
            }

            // 2. Active Attempt Resume Banner (if any)
            if (!activeAttemptTestId.isNullOrBlank()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AccentOrange),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "चल रही परीक्षा (Exam In Progress)",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = "आपकी पिछली परीक्षा अधूरी रह गई थी।",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.92f)
                                )
                            }
                            Button(
                                onClick = {
                                    tests.find { it.id == activeAttemptTestId }?.let(onStartTest)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.minimumInteractiveComponentSize()
                            ) {
                                Text(
                                    text = "जारी रखें",
                                    color = AccentOrange,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // 3. Student Performance Statistics Overview
            item {
                val publishedTestsCount = tests.count { it.isPublished }
                val avgScore = if (results.isNotEmpty()) {
                    String.format("%.1f%%", results.map { it.percentage }.average())
                } else {
                    "0%"
                }
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        HomeStatItem(label = "कुल टेस्ट", value = "$publishedTestsCount", color = PrimaryBlue)
                        HomeStatItem(label = "दी गई परीक्षाएं", value = "${results.size}", color = AccentEmerald)
                        HomeStatItem(label = "औसत स्कोर", value = avgScore, color = AccentOrange)
                    }
                }
            }

            // 4. Exam Sections Header
            item {
                Column {
                    Text(
                        text = "परीक्षा अनुभाग (Exam Sections)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "अपनी लक्ष्य परीक्षा चुनें और कक्षा-वार टेस्ट दें:",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B)
                    )
                }
            }

            // 5. Ordered Exam Cards (Navodaya -> Sainik School -> Simultala -> Olympiad -> Railway -> SSC -> All Mixed)
            items(sections, key = { it.id }) { section ->
                val sectionTests = if (section.id == "All") {
                    tests.filter { it.isPublished }
                } else {
                    tests.filter { it.isPublished && it.category.equals(section.id, ignoreCase = true) }
                }
                val attemptedCount = sectionTests.count { t -> results.any { it.testId == t.id } }
                val badge = if (section.id == "All") {
                    "${sectionTests.size} कुल टेस्ट उपलब्ध"
                } else {
                    "${sectionTests.size} टेस्ट • $attemptedCount पूर्ण"
                }

                HomeExamSectionCard(
                    section = section,
                    badge = badge,
                    gradient = ExamVisualTheme.getGradientForExam(section.id),
                    icon = ExamVisualTheme.getIconForExam(section.id),
                    onClick = { onOpenSection(section.id) }
                )
            }

            // 6. Tools & Reports Section
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "अन्य सुविधाएं एवं रिपोर्ट (Tools & Reports)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF0F172A)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    UtilityButtonCard(
                        title = "परीक्षा परिणाम",
                        subtitle = "स्कोरकार्ड व हल",
                        icon = Icons.Default.Assignment,
                        color = Color(0xFF0284C7),
                        modifier = Modifier.weight(1f),
                        onClick = onViewResults
                    )
                    UtilityButtonCard(
                        title = "लाइव लीडरबोर्ड",
                        subtitle = "ऑल इंडिया रैंक",
                        icon = Icons.Default.Leaderboard,
                        color = Color(0xFF7C3AED),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val defaultTest = tests.firstOrNull()?.id ?: "test_railway_math_01"
                            onViewLeaderboard(defaultTest)
                        }
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    UtilityButtonCard(
                        title = "सीखने का विश्लेषण",
                        subtitle = "स्पीड व शुद्धता",
                        icon = Icons.Default.Analytics,
                        color = Color(0xFF059669),
                        modifier = Modifier.weight(1f),
                        onClick = onViewAnalysis
                    )
                    UtilityButtonCard(
                        title = "मिथलेश सर वीडियो",
                        subtitle = "शॉर्टकट ट्रिक्स",
                        icon = Icons.Default.PlayCircle,
                        color = Color(0xFFDC2626),
                        modifier = Modifier.weight(1f),
                        onClick = onViewVideos
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun HomeExamSectionCard(
    section: ExamSectionInfo,
    badge: String,
    gradient: List<Color>,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .testTag("exam_section_card_${section.id.lowercase().replace(" ", "_")}")
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(gradient))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier.size(50.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = section.titleEn,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = section.titleHi,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = section.titleEn,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Color(0xFFFDE047),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = section.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.88f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White.copy(alpha = 0.22f)
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.25f),
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open ${section.titleEn}",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeStatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF64748B)
        )
    }
}

@Composable
fun UtilityButtonCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier
            .height(86.dp)
            .minimumInteractiveComponentSize()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = color.copy(alpha = 0.12f),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = color,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF0F172A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF64748B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
