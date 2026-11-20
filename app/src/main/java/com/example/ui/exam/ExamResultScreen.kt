package com.example.ui.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamResult
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SurfaceSoft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamResultScreen(
    result: ExamResult,
    questions: List<ExamQuestion>,
    onViewLeaderboard: (String) -> Unit,
    onBackToHome: () -> Unit,
    savedQuestionIds: Set<String> = emptySet(),
    onToggleSaveQuestion: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var explanationLanguage by remember { mutableStateOf("HI") }

    val minutes = result.timeTakenSeconds / 60
    val seconds = result.timeTakenSeconds % 60
    val timeFormatted = "${minutes}m ${seconds}s"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "परीक्षा परिणाम (Scorecard)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                },
                actions = {
                    Row(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (explanationLanguage == "EN") "English" else "हिन्दी",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
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
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Score Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(PrimaryBlue, Color(0xFF1E293B))
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.2f),
                                modifier = Modifier.size(60.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.EmojiEvents,
                                        contentDescription = "Trophy",
                                        tint = Color(0xFFFDE047),
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = result.testTitle,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = "परीक्षार्थी: ${result.studentName} (${result.studentId})",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.85f)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Score & Percentage
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                HeroStatItem(
                                    label = "प्राप्तांक (Score)",
                                    value = "${result.totalMarks} / ${result.maxMarks.toInt()}"
                                )
                                HeroStatItem(
                                    label = "प्रतिशत (Score %)",
                                    value = "${result.percentage}%"
                                )
                                HeroStatItem(
                                    label = "सटीकता (Accuracy)",
                                    value = "${result.accuracy}%"
                                )
                            }
                        }
                    }
                }
            }

            // Performance Breakdown Grid
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "प्रयास विश्लेषण (Attempt Breakdown)",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF0F172A)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BreakdownItem(
                                icon = Icons.Default.CheckCircle,
                                color = AccentEmerald,
                                label = "सही (Correct)",
                                count = "${result.correctAnswers}"
                            )
                            BreakdownItem(
                                icon = Icons.Default.Close,
                                color = Color(0xFFDC2626),
                                label = "गलत (Incorrect)",
                                count = "${result.incorrectAnswers}"
                            )
                            BreakdownItem(
                                icon = Icons.Default.QuestionMark,
                                color = Color(0xFF94A3B8),
                                label = "छोड़ा गया (Unattempted)",
                                count = "${result.unattempted}"
                            )
                            BreakdownItem(
                                icon = Icons.Default.Timer,
                                color = AccentOrange,
                                label = "समय (Time Taken)",
                                count = timeFormatted
                            )
                        }
                    }
                }
            }

            // Leaderboard & Home Navigation Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onViewLeaderboard(result.testId) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AccentPurple),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("result_view_leaderboard_button")
                    ) {
                        Icon(imageVector = Icons.Default.Leaderboard, contentDescription = "Leaderboard")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("लीडरबोर्ड देखें", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onBackToHome,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("result_back_to_home_button")
                    ) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = PrimaryBlue)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("होम स्क्रीन", color = PrimaryBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Detailed Solution Explanation Header & Language Switcher
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "विस्तृत प्रश्न हल एवं व्याख्या (Solutions)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF0F172A)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        FilterChip(
                            selected = explanationLanguage == "EN",
                            onClick = { explanationLanguage = "EN" },
                            label = { Text("EN") },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = PrimaryBlue, selectedLabelColor = Color.White)
                        )
                        FilterChip(
                            selected = explanationLanguage == "HI",
                            onClick = { explanationLanguage = "HI" },
                            label = { Text("हिन्दी") },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = PrimaryBlue, selectedLabelColor = Color.White)
                        )
                    }
                }
            }

            // Question by Question Analysis
            itemsIndexed(questions) { index, q ->
                val userChoice = result.userAnswers[q.id]
                val correctChoice = result.correctKeys[q.id]
                val isCorrect = !userChoice.isNullOrBlank() && userChoice.equals(correctChoice, ignoreCase = true)
                val isSkipped = userChoice.isNullOrBlank()

                val qText = if (explanationLanguage == "HI" && q.questionHi.isNotBlank()) q.questionHi else q.questionEn
                val explanation = if (explanationLanguage == "HI") {
                    result.explanationsHi[q.id] ?: result.explanationsEn[q.id] ?: "हल उपलब्ध है।"
                } else {
                    result.explanationsEn[q.id] ?: result.explanationsHi[q.id] ?: "Explanation available."
                }

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "प्रश्न ${index + 1}",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryBlue
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Status Tag
                                val (tagBg, tagText, tagColor) = when {
                                    isCorrect -> Triple(Color(0xFFECFDF5), "सही (+1)", AccentEmerald)
                                    isSkipped -> Triple(Color(0xFFF1F5F9), "अनुत्तरित (0)", Color(0xFF64748B))
                                    else -> Triple(Color(0xFFFEF2F2), "गलत (-0.33)", Color(0xFFDC2626))
                                }
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = tagBg
                                ) {
                                    Text(
                                        text = tagText,
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = tagColor,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                                if (onToggleSaveQuestion != null) {
                                    val isBookmarked = q.id in savedQuestionIds
                                    IconButton(
                                        onClick = { onToggleSaveQuestion(q.id) },
                                        modifier = Modifier
                                            .minimumInteractiveComponentSize()
                                            .testTag("bookmark_solution_question_${q.id}")
                                    ) {
                                        Icon(
                                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                            contentDescription = if (isBookmarked) "Remove Bookmark" else "Save Question",
                                            tint = if (isBookmarked) AccentOrange else Color(0xFF64748B)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = qText,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = Color(0xFF1E293B)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Choices Row
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Text(
                                text = "आपका उत्तर: ${userChoice ?: "कोई नहीं (Skipped)"}",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = if (isCorrect) AccentEmerald else Color(0xFFDC2626)
                            )
                            Text(
                                text = "सही विकल्प: ${correctChoice ?: "-"}",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = AccentEmerald
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Step-by-Step Mathematical Solution
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF8FAFC))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "हल एवं स्पष्टीकरण (Explanation):",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF475569)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = explanation,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF0F172A)
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
private fun HeroStatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = Color.White
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun BreakdownItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    label: String,
    count: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = count,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF0F172A)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF64748B),
            fontSize = 10.sp
        )
    }
}
