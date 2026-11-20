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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.ExamResult
import com.example.data.model.ExamTest
import com.example.data.model.StudyNoteChapter
import com.example.data.repository.ExamRepository

@Composable
fun ChapterStudyNotesDetailScreen(
    note: StudyNoteChapter,
    linkedTests: List<ExamTest>,
    results: List<ExamResult>,
    onBack: () -> Unit,
    onStartTest: (ExamTest) -> Unit,
    onViewResult: (ExamResult) -> Unit,
    onViewLeaderboard: (String) -> Unit
) {
    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = note.chapterTitleEn,
                subtitle = note.subject,
                classBadge = note.examClass.ifBlank { note.examCategory },
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Chapter Overview Card
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
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = "Concept Overview",
                                tint = DarkExamColors.Blue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Concept Summary (अध्याय सार)",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = DarkExamColors.TextPrimary
                            )
                        }
                        if (note.chapterTitleHi.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = note.chapterTitleHi,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF93C5FD)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = note.summaryEn,
                            style = MaterialTheme.typography.bodyMedium,
                            color = DarkExamColors.TextPrimary
                        )
                        if (note.summaryHi.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = note.summaryHi,
                                style = MaterialTheme.typography.bodySmall,
                                color = DarkExamColors.TextSecondary
                            )
                        }
                    }
                }
            }

            // Key Formulas Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
                    border = BorderStroke(1.dp, DarkExamColors.Blue.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Functions,
                                contentDescription = "Key Formulas",
                                tint = DarkExamColors.Amber,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Key Formulas & Short Tricks",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = DarkExamColors.TextPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        note.keyFormulas.forEachIndexed { idx, formula ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkExamColors.CardElevated)
                                    .padding(10.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "${idx + 1}. ",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = DarkExamColors.Amber
                                )
                                Text(
                                    text = formula,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                    color = DarkExamColors.TextPrimary
                                )
                            }
                        }
                    }
                }
            }

            // Solved Example Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
                    border = BorderStroke(1.dp, DarkExamColors.Emerald.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Solved Example",
                                tint = DarkExamColors.Emerald,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Solved Example (हल उदाहरण)",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = DarkExamColors.TextPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = note.solvedExampleEn,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = DarkExamColors.TextPrimary
                        )
                        if (note.solvedExampleHi.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = note.solvedExampleHi,
                                style = MaterialTheme.typography.bodySmall,
                                color = DarkExamColors.TextSecondary
                            )
                        }
                    }
                }
            }

            // Practice Tests Linked to Chapter
            if (linkedTests.isNotEmpty()) {
                item {
                    Text(
                        text = "Practice Tests for This Topic",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = DarkExamColors.TextPrimary
                    )
                }
                items(linkedTests, key = { it.id }) { test ->
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
fun MostSavedQuestionsScreen(
    examId: String,
    selectedClass: String?,
    itemsList: List<ExamRepository.ContextQuestionItem>,
    savedQuestionIds: Set<String>,
    onToggleSave: (String) -> Unit,
    onBack: () -> Unit
) {
    var language by remember { mutableStateOf("HI") }
    val revealedMap = remember { mutableStateMapOf<String, Boolean>() }

    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = "Most Saved Questions",
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${itemsList.size} Revision Questions",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = DarkExamColors.TextPrimary
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = language == "EN",
                            onClick = { language = "EN" },
                            label = { Text("EN") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = DarkExamColors.BlueDeep,
                                selectedLabelColor = Color.White,
                                containerColor = DarkExamColors.Card,
                                labelColor = DarkExamColors.TextSecondary
                            )
                        )
                        FilterChip(
                            selected = language == "HI",
                            onClick = { language = "HI" },
                            label = { Text("हिन्दी") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = DarkExamColors.BlueDeep,
                                selectedLabelColor = Color.White,
                                containerColor = DarkExamColors.Card,
                                labelColor = DarkExamColors.TextSecondary
                            )
                        )
                    }
                }
            }

            if (itemsList.isEmpty()) {
                item {
                    DarkEmptyTestsCard("कोई सहेजा गया या महत्वपूर्ण प्रश्न उपलब्ध नहीं है")
                }
            } else {
                itemsIndexed(itemsList, key = { _, item -> item.question.id }) { idx, item ->
                    val q = item.question
                    val isBookmarked = q.id in savedQuestionIds
                    val isRevealed = revealedMap[q.id] == true
                    val qPrompt = if (language == "HI" && q.questionHi.isNotBlank()) q.questionHi else q.questionEn
                    val options = if (language == "HI" && q.optionsHi.isNotEmpty()) q.optionsHi else q.optionsEn
                    val explanation = if (language == "HI" && item.explanationHi.isNotBlank()) item.explanationHi else item.explanationEn

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
                        border = BorderStroke(1.dp, DarkExamColors.Border),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("saved_q_${q.id}")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (isBookmarked) DarkExamColors.Amber.copy(alpha = 0.2f) else DarkExamColors.CardElevated
                                ) {
                                    Text(
                                        text = if (isBookmarked) "Q${idx + 1} • Saved by You" else "Q${idx + 1} • ${item.test.subject}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (isBookmarked) Color(0xFFFCD34D) else Color(0xFF93C5FD),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                IconButton(
                                    onClick = { onToggleSave(q.id) },
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .testTag("bookmark_toggle_${q.id}")
                                ) {
                                    Icon(
                                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = if (isBookmarked) "Remove Bookmark" else "Save Question",
                                        tint = if (isBookmarked) DarkExamColors.Amber else DarkExamColors.TextSecondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = qPrompt,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = DarkExamColors.TextPrimary
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            options.forEach { opt ->
                                val letter = opt.take(1)
                                val highlightCorrect = isRevealed && letter.equals(item.correctOption, ignoreCase = true)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 3.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (highlightCorrect) DarkExamColors.Emerald.copy(alpha = 0.2f)
                                            else DarkExamColors.CardElevated
                                        )
                                        .padding(horizontal = 10.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = opt,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = if (highlightCorrect) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        color = if (highlightCorrect) DarkExamColors.Emerald else DarkExamColors.TextPrimary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkExamColors.Blue.copy(alpha = 0.14f))
                                    .minimumInteractiveComponentSize()
                                    .clickable { revealedMap[q.id] = !isRevealed }
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = if (isRevealed) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Toggle Solution",
                                    tint = Color(0xFF93C5FD),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isRevealed) "Hide Answer & Explanation" else "Show Answer & Explanation",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF93C5FD)
                                )
                            }

                            if (isRevealed) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = DarkExamColors.CardElevated,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "Correct Answer: Option ${item.correctOption}",
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                            color = DarkExamColors.Emerald
                                        )
                                        if (explanation.isNotBlank()) {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = explanation,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = DarkExamColors.TextSecondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
