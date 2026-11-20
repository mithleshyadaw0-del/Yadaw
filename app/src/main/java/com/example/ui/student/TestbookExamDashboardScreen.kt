package com.example.ui.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExamResult
import com.example.data.model.ExamTest
import com.example.data.model.StudyNoteChapter

@Composable
fun TestbookExamDashboardScreen(
    examId: String,
    selectedClass: String?,
    selectedTab: ExamDashboardTab,
    selectedNotesSubject: String?,
    allExamTests: List<ExamTest>,
    pyqTests: List<ExamTest>,
    marathonTests: List<ExamTest>,
    savedQuestionsCount: Int,
    examResults: List<ExamResult>,
    noteSubjects: List<String>,
    notesForSelectedSubject: List<StudyNoteChapter>,
    supportsClassChange: Boolean,
    onChangeClass: () -> Unit,
    onBack: () -> Unit,
    onSelectTab: (ExamDashboardTab) -> Unit,
    onSelectNotesSubject: (String) -> Unit,
    onOpenNoteDetail: (String) -> Unit,
    onOpenPyqMarathon: () -> Unit,
    onOpenMostSavedQuestions: () -> Unit,
    onOpenChapterWise: () -> Unit,
    onOpenSubjectWise: () -> Unit,
    onOpenSectional: () -> Unit,
    onOpenFullLength: () -> Unit,
    onOpenAdvanced: () -> Unit,
    onOpenPyqList: (String) -> Unit,
    onOpenMyResults: () -> Unit
) {
    val examTitle = ExamVisualTheme.getTitleForExam(examId)
    val examIcon = ExamVisualTheme.getIconForExam(examId)

    val attemptedCount = examResults.size
    val avgScore = if (examResults.isNotEmpty()) String.format("%.1f%%", examResults.map { it.percentage }.average()) else "0%"
    val avgAccuracy = if (examResults.isNotEmpty()) String.format("%.1f%%", examResults.map { it.accuracy }.average()) else "0%"

    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = examTitle,
                subtitle = "Maths By Mithlesh Sir",
                examIcon = examIcon,
                classBadge = selectedClass,
                onBack = onBack,
                onChangeClass = if (supportsClassChange) onChangeClass else null
            )
        },
        containerColor = DarkExamColors.Bg
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // 1. Performance Summary Card
                item {
                    PerformanceSummaryCard(
                        attemptedCount = attemptedCount,
                        totalTestsCount = allExamTests.size,
                        avgScore = avgScore,
                        avgAccuracy = avgAccuracy,
                        onViewFullAnalysis = onOpenMyResults
                    )
                }

                // 2. Three Horizontal Tabs (MOCK TESTS | PYQs | STUDY NOTES)
                item {
                    DashboardHorizontalTabStrip(
                        selectedTab = selectedTab,
                        onSelectTab = onSelectTab
                    )
                }

                // 3. Tab Body (switches in-place without leaving dashboard)
                when (selectedTab) {
                    ExamDashboardTab.MOCK_TESTS -> {
                        val chapterTests = allExamTests.filter { it.testType == "CHAPTER_WISE" }
                        val subjectWiseTests = allExamTests.filter { it.testType == "SUBJECT_WISE" }
                        val sectionalTests = allExamTests.filter { it.testType == "SECTIONAL" }
                        val fullLengthTests = allExamTests.filter { it.testType.isBlank() || it.testType == "FULL_LENGTH" }
                        val advancedTests = allExamTests.filter { it.testType == "ADVANCED" }

                        val rows = listOf(
                            MockCategoryRowModel(
                                count = marathonTests.size,
                                title = "PYQ Marathon Live Test",
                                subtitle = "Fast-track Previous Year live marathon sets",
                                freeCount = marathonTests.count { it.isFree },
                                testTag = "mock_cat_pyq_marathon",
                                onClick = onOpenPyqMarathon
                            ),
                            MockCategoryRowModel(
                                count = savedQuestionsCount,
                                title = "Most Saved Questions",
                                subtitle = "Your bookmarked & high-yield revision bank",
                                freeCount = 0,
                                isSavedRow = true,
                                testTag = "mock_cat_saved_questions",
                                onClick = onOpenMostSavedQuestions
                            ),
                            MockCategoryRowModel(
                                count = chapterTests.size,
                                title = "Chapter Wise Test",
                                subtitle = "Subject & chapter drill-down practice",
                                freeCount = chapterTests.count { it.isFree },
                                testTag = "mock_cat_chapter_wise",
                                onClick = onOpenChapterWise
                            ),
                            MockCategoryRowModel(
                                count = subjectWiseTests.size,
                                title = "Subject Wise Test",
                                subtitle = "Complete subject evaluation tests",
                                freeCount = subjectWiseTests.count { it.isFree },
                                testTag = "mock_cat_subject_wise",
                                onClick = onOpenSubjectWise
                            ),
                            MockCategoryRowModel(
                                count = sectionalTests.size,
                                title = "Sectional Test",
                                subtitle = "Timed exam-syllabus sectional papers",
                                freeCount = sectionalTests.count { it.isFree },
                                testTag = "mock_cat_sectional",
                                onClick = onOpenSectional
                            ),
                            MockCategoryRowModel(
                                count = fullLengthTests.size,
                                title = "Full Length Test",
                                subtitle = "Complete mock, model & practice papers",
                                freeCount = fullLengthTests.count { it.isFree },
                                testTag = "mock_cat_full_length",
                                onClick = onOpenFullLength
                            ),
                            MockCategoryRowModel(
                                count = advancedTests.size,
                                title = "Advanced Test",
                                subtitle = "High-difficulty merit & rank booster sets",
                                freeCount = advancedTests.count { it.isFree },
                                testTag = "mock_cat_advanced",
                                onClick = onOpenAdvanced
                            )
                        )

                        items(rows, key = { it.testTag }) { row ->
                            DashboardVerticalCategoryRow(model = row)
                        }
                    }

                    ExamDashboardTab.PYQS -> {
                        val years = pyqTests.mapNotNull { it.examYear.takeIf { y -> y.isNotBlank() } }
                            .distinct()
                            .sortedDescending()
                        val subjectWisePyqCount = pyqTests.count { !it.subject.equals("All Subjects", true) }
                        val marathonPyqCount = pyqTests.count { it.subType.equals("Marathon", true) }

                        val pyqRows = buildList {
                            add(
                                MockCategoryRowModel(
                                    count = pyqTests.size,
                                    title = "Previous Year Papers",
                                    subtitle = "All official memory-based & past exam papers",
                                    freeCount = pyqTests.count { it.isFree },
                                    testTag = "pyq_cat_all",
                                    onClick = { onOpenPyqList("All") }
                                )
                            )
                            years.forEach { yr ->
                                val yearTests = pyqTests.filter { it.examYear == yr }
                                add(
                                    MockCategoryRowModel(
                                        count = yearTests.size,
                                        title = "Previous Year Question Sets ($yr)",
                                        subtitle = "${selectedClass ?: examId} • Year $yr Question Papers",
                                        freeCount = yearTests.count { it.isFree },
                                        testTag = "pyq_cat_year_$yr",
                                        onClick = { onOpenPyqList(yr) }
                                    )
                                )
                            }
                            add(
                                MockCategoryRowModel(
                                    count = pyqTests.size,
                                    title = "Previous Year Mock Tests",
                                    subtitle = "Attempt past papers in real CBT mode",
                                    freeCount = pyqTests.count { it.isFree },
                                    testTag = "pyq_cat_mock_sets",
                                    onClick = { onOpenPyqList("Mock Sets") }
                                )
                            )
                            add(
                                MockCategoryRowModel(
                                    count = subjectWisePyqCount,
                                    title = "Subject-wise PYQs",
                                    subtitle = "Focused previous year subject sets",
                                    freeCount = subjectWisePyqCount,
                                    testTag = "pyq_cat_subject_wise",
                                    onClick = { onOpenPyqList("Subject-wise") }
                                )
                            )
                            if (marathonPyqCount > 0) {
                                add(
                                    MockCategoryRowModel(
                                        count = marathonPyqCount,
                                        title = "PYQ Marathon Question Sets",
                                        subtitle = "Rapid revision past paper marathons",
                                        freeCount = marathonPyqCount,
                                        testTag = "pyq_cat_marathon",
                                        onClick = { onOpenPyqList("Marathon") }
                                    )
                                )
                            }
                        }

                        items(pyqRows, key = { it.testTag }) { row ->
                            DashboardVerticalCategoryRow(model = row)
                        }
                    }

                    ExamDashboardTab.STUDY_NOTES -> {
                        item {
                            Spacer(modifier = Modifier.height(14.dp))
                            if (noteSubjects.isNotEmpty()) {
                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(noteSubjects) { subj ->
                                        val isSelected = subj.equals(selectedNotesSubject, ignoreCase = true)
                                        val displayLabel = shortSubjectTabLabel(subj)
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = if (isSelected) DarkExamColors.BlueDeep else DarkExamColors.Card,
                                            border = BorderStroke(
                                                1.dp,
                                                if (isSelected) DarkExamColors.Blue else DarkExamColors.Border
                                            ),
                                            onClick = { onSelectNotesSubject(subj) },
                                            modifier = Modifier
                                                .minimumInteractiveComponentSize()
                                                .testTag("notes_subject_pill_${subj.lowercase().replace(" ", "_")}")
                                        ) {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                                            ) {
                                                Text(
                                                    text = displayLabel,
                                                    style = MaterialTheme.typography.labelLarge.copy(
                                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                                    ),
                                                    color = if (isSelected) Color.White else DarkExamColors.TextSecondary
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                            }
                        }

                        if (notesForSelectedSubject.isEmpty()) {
                            item {
                                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    DarkEmptyTestsCard("इस विषय के अध्ययन नोट्स जल्द उपलब्ध होंगे")
                                }
                            }
                        } else {
                            items(notesForSelectedSubject, key = { it.id }) { note ->
                                StudyNoteChapterCard(
                                    note = note,
                                    onClick = { onOpenNoteDetail(note.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun shortSubjectTabLabel(subject: String): String = when {
    subject.contains("Reasoning", true) || subject.contains("Mental", true) -> "Mental Ability / Reasoning"
    subject.contains("Hindi", true) -> "Hindi / Language"
    else -> subject
}

@Composable
private fun PerformanceSummaryCard(
    attemptedCount: Int,
    totalTestsCount: Int,
    avgScore: String,
    avgAccuracy: String,
    onViewFullAnalysis: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
        border = BorderStroke(1.dp, DarkExamColors.Border),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Performance Summary",
                        tint = DarkExamColors.Amber,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Performance Summary",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = DarkExamColors.TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SummaryMetricCol(
                        value = "$attemptedCount / $totalTestsCount",
                        label = "Tests Attempted",
                        accent = Color(0xFF93C5FD)
                    )
                    SummaryMetricCol(
                        value = avgScore,
                        label = "Average Score",
                        accent = DarkExamColors.Emerald
                    )
                    SummaryMetricCol(
                        value = avgAccuracy,
                        label = "Accuracy",
                        accent = Color(0xFFFCD34D)
                    )
                }
            }

            HorizontalDivider(color = DarkExamColors.Divider, thickness = 1.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .minimumInteractiveComponentSize()
                    .testTag("view_full_performance_analysis")
                    .clickable { onViewFullAnalysis() }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Analytics,
                        contentDescription = null,
                        tint = DarkExamColors.Blue,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "View Full Performance Analysis",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Color(0xFF93C5FD)
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Open Analysis",
                    tint = DarkExamColors.TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun SummaryMetricCol(value: String, label: String, accent: Color) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
            color = accent
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = DarkExamColors.TextSecondary
        )
    }
}

@Composable
private fun DashboardHorizontalTabStrip(
    selectedTab: ExamDashboardTab,
    onSelectTab: (ExamDashboardTab) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkExamColors.TopBar)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ExamDashboardTab.entries.forEach { tab ->
                val isSelected = selectedTab == tab
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .minimumInteractiveComponentSize()
                        .testTag("dashboard_tab_${tab.name.lowercase()}")
                        .clickable { onSelectTab(tab) }
                        .padding(top = 12.dp)
                ) {
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        ),
                        color = if (isSelected) DarkExamColors.TextPrimary else DarkExamColors.TextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(3.dp)
                            .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                            .background(if (isSelected) DarkExamColors.Blue else Color.Transparent)
                    )
                }
            }
        }
        HorizontalDivider(color = DarkExamColors.Divider, thickness = 1.dp)
    }
}

private data class MockCategoryRowModel(
    val count: Int,
    val title: String,
    val subtitle: String,
    val freeCount: Int,
    val isSavedRow: Boolean = false,
    val testTag: String,
    val onClick: () -> Unit
)

@Composable
private fun DashboardVerticalCategoryRow(model: MockCategoryRowModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkExamColors.Bg)
            .minimumInteractiveComponentSize()
            .testTag(model.testTag)
            .clickable { model.onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = DarkExamColors.CardElevated,
                modifier = Modifier.width(46.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "${model.count}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = DarkExamColors.TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = model.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = DarkExamColors.TextPrimary
                    )
                    if (model.isSavedRow) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Saved",
                            tint = DarkExamColors.Amber,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = model.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = DarkExamColors.TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (model.freeCount > 0) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${model.freeCount} Free",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = DarkExamColors.Emerald
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open ${model.title}",
                tint = DarkExamColors.TextSecondary,
                modifier = Modifier.size(22.dp)
            )
        }
        HorizontalDivider(
            color = DarkExamColors.Divider,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 76.dp, end = 16.dp)
        )
    }
}

@Composable
private fun StudyNoteChapterCard(
    note: StudyNoteChapter,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
        border = BorderStroke(1.dp, DarkExamColors.Border),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .minimumInteractiveComponentSize()
            .testTag("study_note_card_${note.id}")
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = DarkExamColors.Blue.copy(alpha = 0.16f),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = note.chapterTitleEn,
                        tint = Color(0xFF60A5FA),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.chapterTitleEn,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = DarkExamColors.TextPrimary
                )
                if (note.chapterTitleHi.isNotBlank()) {
                    Text(
                        text = note.chapterTitleHi,
                        style = MaterialTheme.typography.bodySmall,
                        color = DarkExamColors.TextSecondary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "${note.keyFormulas.size} Key Formulas",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = Color(0xFF93C5FD)
                    )
                    if (note.linkedTestIds.isNotEmpty()) {
                        Text(
                            text = "• ${note.linkedTestIds.size} Practice Tests",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = DarkExamColors.Emerald
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open Notes",
                tint = DarkExamColors.TextSecondary,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
