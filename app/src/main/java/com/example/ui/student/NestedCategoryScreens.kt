package com.example.ui.student

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import com.example.data.model.ExamResult
import com.example.data.model.ExamTest

@Composable
fun SubjectSelectionScreen(
    examId: String,
    selectedClass: String?,
    modeTitle: String,
    subjects: List<String>,
    allTestsForType: List<ExamTest>,
    results: List<ExamResult>,
    onSelectSubject: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = modeTitle,
                subtitle = "${ExamVisualTheme.getTitleForExam(examId)} • Select Subject",
                classBadge = selectedClass,
                onBack = onBack
            )
        },
        containerColor = DarkExamColors.Bg
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Select Subject (विषय चुनें)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = DarkExamColors.TextPrimary
                )
                Text(
                    text = "Showing only subjects applicable to ${selectedClass ?: examId} syllabus:",
                    style = MaterialTheme.typography.bodySmall,
                    color = DarkExamColors.TextSecondary
                )
            }

            if (subjects.isEmpty()) {
                item {
                    DarkEmptyTestsCard("इस श्रेणी में अभी कोई विषय उपलब्ध नहीं है")
                }
            } else {
                items(subjects) { subject ->
                    val subjectTests = allTestsForType.filter { it.subject.equals(subject, ignoreCase = true) }
                    val completedCount = subjectTests.count { t -> results.any { it.testId == t.id } }
                    val (hindiLabel, icon, color) = ExamVisualTheme.getSubjectMeta(subject)
                    val accent = Color(
                        red = (color.red + 0.25f).coerceAtMost(1f),
                        green = (color.green + 0.25f).coerceAtMost(1f),
                        blue = (color.blue + 0.25f).coerceAtMost(1f)
                    )
                    val progress = if (subjectTests.isNotEmpty()) completedCount.toFloat() / subjectTests.size else 0f

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
                        border = BorderStroke(1.dp, DarkExamColors.Border),
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("subject_card_${subject.lowercase().replace(" ", "_")}")
                            .clickable { onSelectSubject(subject) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = accent.copy(alpha = 0.16f),
                                    modifier = Modifier.size(46.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = subject,
                                            tint = accent,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = subject,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = DarkExamColors.TextPrimary
                                    )
                                    Text(
                                        text = hindiLabel,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = DarkExamColors.TextSecondary
                                    )
                                }

                                Surface(
                                    shape = CircleShape,
                                    color = DarkExamColors.CardElevated,
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.ChevronRight,
                                            contentDescription = "Open $subject",
                                            tint = DarkExamColors.TextSecondary
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            LinearProgressIndicator(
                                progress = { progress },
                                color = accent,
                                trackColor = DarkExamColors.Divider,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${subjectTests.size} Tests Available",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = DarkExamColors.TextSecondary
                                )
                                Text(
                                    text = "$completedCount / ${subjectTests.size} Completed",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = accent
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChapterListScreen(
    examId: String,
    selectedClass: String?,
    subject: String,
    chapterTests: List<ExamTest>,
    results: List<ExamResult>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSelectChapter: (String) -> Unit,
    onBack: () -> Unit
) {
    val completedCount = chapterTests.count { t -> results.any { it.testId == t.id } }
    val groupedChapters = chapterTests
        .groupBy { it.chapter.ifBlank { it.title } }
        .entries
        .filter { (chapterName, tests) ->
            searchQuery.isBlank() ||
                chapterName.contains(searchQuery, ignoreCase = true) ||
                tests.any {
                    it.title.contains(searchQuery, ignoreCase = true) ||
                        it.titleHindi.contains(searchQuery, ignoreCase = true)
                }
        }

    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = "$subject • Chapters",
                subtitle = ExamVisualTheme.getTitleForExam(examId),
                classBadge = selectedClass,
                onBack = onBack
            )
        },
        containerColor = DarkExamColors.Bg
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
                    border = BorderStroke(1.dp, DarkExamColors.Emerald.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Chapter Completion Status",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = DarkExamColors.TextPrimary
                            )
                            Text(
                                text = "$completedCount of ${chapterTests.size} Chapter Tests Completed",
                                style = MaterialTheme.typography.bodySmall,
                                color = DarkExamColors.Emerald
                            )
                        }
                        Icon(
                            imageVector = if (completedCount == chapterTests.size && chapterTests.isNotEmpty())
                                Icons.Default.CheckCircle else Icons.Default.Schedule,
                            contentDescription = "Progress",
                            tint = DarkExamColors.Emerald,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }

            item {
                DarkSearchField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = "Search chapters (अध्याय खोजें)..."
                )
            }

            if (groupedChapters.isEmpty()) {
                item { DarkEmptyTestsCard("इस विषय में कोई अध्याय टेस्ट उपलब्ध नहीं है") }
            } else {
                items(groupedChapters, key = { it.key }) { (chapterName, testsInChapter) ->
                    val doneInChap = testsInChapter.count { t -> results.any { it.testId == t.id } }
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
                        border = BorderStroke(1.dp, DarkExamColors.Border),
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("chapter_card_${chapterName.take(20).lowercase().replace(" ", "_")}")
                            .clickable { onSelectChapter(chapterName) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = DarkExamColors.Emerald.copy(alpha = 0.16f),
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.LibraryBooks,
                                        contentDescription = chapterName,
                                        tint = DarkExamColors.Emerald,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = chapterName,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = DarkExamColors.TextPrimary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${testsInChapter.size} Test(s) • $doneInChap Completed",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (doneInChap > 0) DarkExamColors.Emerald else DarkExamColors.TextSecondary
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Open Chapter Tests",
                                tint = DarkExamColors.TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChapterTestListScreen(
    examId: String,
    selectedClass: String?,
    subject: String,
    chapterName: String,
    chapterTests: List<ExamTest>,
    results: List<ExamResult>,
    onBack: () -> Unit,
    onStartTest: (ExamTest) -> Unit,
    onViewResult: (ExamResult) -> Unit,
    onViewLeaderboard: (String) -> Unit
) {
    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = chapterName,
                subtitle = "$subject • ${ExamVisualTheme.getTitleForExam(examId)}",
                classBadge = selectedClass,
                onBack = onBack
            )
        },
        containerColor = DarkExamColors.Bg
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (chapterTests.isEmpty()) {
                item { DarkEmptyTestsCard("इस अध्याय में कोई टेस्ट उपलब्ध नहीं है") }
            } else {
                items(chapterTests, key = { it.id }) { test ->
                    val existing = results.firstOrNull { it.testId == test.id }
                    DarkExamTestCardItem(
                        test = test,
                        existingResult = existing,
                        onStartClick = { onStartTest(test) },
                        onViewResultClick = { existing?.let(onViewResult) },
                        onLeaderboardClick = { onViewLeaderboard(test.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun SubjectTestsListScreen(
    examId: String,
    selectedClass: String?,
    subject: String,
    modeLabel: String,
    subjectTests: List<ExamTest>,
    results: List<ExamResult>,
    onBack: () -> Unit,
    onStartTest: (ExamTest) -> Unit,
    onViewResult: (ExamResult) -> Unit,
    onViewLeaderboard: (String) -> Unit
) {
    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = "$subject • $modeLabel",
                subtitle = ExamVisualTheme.getTitleForExam(examId),
                classBadge = selectedClass,
                onBack = onBack
            )
        },
        containerColor = DarkExamColors.Bg
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (subjectTests.isEmpty()) {
                item { DarkEmptyTestsCard("इस विषय में कोई टेस्ट उपलब्ध नहीं है") }
            } else {
                items(subjectTests, key = { it.id }) { test ->
                    val existing = results.firstOrNull { it.testId == test.id }
                    DarkExamTestCardItem(
                        test = test,
                        existingResult = existing,
                        onStartClick = { onStartTest(test) },
                        onViewResultClick = { existing?.let(onViewResult) },
                        onLeaderboardClick = { onViewLeaderboard(test.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ExamMyResultsScreen(
    examId: String,
    selectedClass: String?,
    examResults: List<ExamResult>,
    onBack: () -> Unit,
    onViewResult: (ExamResult) -> Unit
) {
    val totalCorrect = examResults.sumOf { it.correctAnswers }
    val totalIncorrect = examResults.sumOf { it.incorrectAnswers }
    val totalMarksScored = examResults.sumOf { it.totalMarks }
    val totalMaxMarks = examResults.sumOf { it.maxMarks }
    val avgAccuracy = if (examResults.isNotEmpty()) String.format("%.1f%%", examResults.map { it.accuracy }.average()) else "0%"
    val avgPercentage = if (examResults.isNotEmpty()) String.format("%.1f%%", examResults.map { it.percentage }.average()) else "0%"

    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = "Performance Analysis",
                subtitle = ExamVisualTheme.getTitleForExam(examId),
                classBadge = selectedClass,
                onBack = onBack
            )
        },
        containerColor = DarkExamColors.Bg
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
                    border = BorderStroke(1.dp, DarkExamColors.Border),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Analytics,
                                contentDescription = "Analysis",
                                tint = DarkExamColors.Blue
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Overall Performance Breakdown",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = DarkExamColors.TextPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            ResultStatCol("Attempted", "${examResults.size}", Color(0xFF93C5FD))
                            ResultStatCol("Marks", "${String.format("%.1f", totalMarksScored)}/${totalMaxMarks.toInt()}", DarkExamColors.Emerald)
                            ResultStatCol("Accuracy", avgAccuracy, Color(0xFFC4B5FD))
                            ResultStatCol("Avg %", avgPercentage, DarkExamColors.Amber)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = DarkExamColors.Divider)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "Correct: $totalCorrect",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = DarkExamColors.Emerald
                            )
                            Text(
                                text = "Incorrect: $totalIncorrect",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFFFCA5A5)
                            )
                        }
                    }
                }
            }

            if (examResults.isEmpty()) {
                item { DarkEmptyTestsCard("इस परीक्षा में अभी तक कोई टेस्ट सबमिट नहीं किया गया है") }
            } else {
                items(examResults, key = { it.id }) { res ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
                        border = BorderStroke(1.dp, DarkExamColors.Border),
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .clickable { onViewResult(res) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = res.testTitle,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = DarkExamColors.TextPrimary,
                                    modifier = Modifier.weight(1f)
                                )
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = DarkExamColors.Emerald.copy(alpha = 0.16f)
                                ) {
                                    Text(
                                        text = "${res.percentage}%",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold),
                                        color = DarkExamColors.Emerald,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Score: ${res.totalMarks}/${res.maxMarks} • Correct: ${res.correctAnswers} • Incorrect: ${res.incorrectAnswers} • Accuracy: ${res.accuracy}%",
                                style = MaterialTheme.typography.bodySmall,
                                color = DarkExamColors.TextSecondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Tap to view step-by-step solutions →",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF93C5FD)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ResultStatCol(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = DarkExamColors.TextSecondary
        )
    }
}
