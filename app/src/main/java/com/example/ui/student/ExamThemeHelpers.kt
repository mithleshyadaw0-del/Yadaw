package com.example.ui.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsRailway
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExamResult
import com.example.data.model.ExamTest
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryBlueLight

object ExamVisualTheme {

    fun getGradientForExam(examId: String?): List<Color> = when {
        examId.equals("Navodaya", true) -> listOf(Color(0xFF1E3A8A), Color(0xFF2563EB))
        examId.equals("Sainik School", true) -> listOf(Color(0xFFC2410C), Color(0xFFEA580C))
        examId.equals("Simultala", true) -> listOf(Color(0xFF047857), Color(0xFF059669))
        examId.equals("Olympiad", true) -> listOf(Color(0xFF6D28D9), Color(0xFF7C3AED))
        examId.equals("Railway", true) -> listOf(Color(0xFF0369A1), Color(0xFF0284C7))
        examId.equals("SSC", true) -> listOf(Color(0xFF0F766E), Color(0xFF14B8A6))
        else -> listOf(Color(0xFF4338CA), Color(0xFF6366F1))
    }

    fun getIconForExam(examId: String?): ImageVector = when {
        examId.equals("Navodaya", true) -> Icons.Default.School
        examId.equals("Sainik School", true) -> Icons.Default.Shield
        examId.equals("Simultala", true) -> Icons.Default.AccountBalance
        examId.equals("Olympiad", true) -> Icons.Default.EmojiEvents
        examId.equals("Railway", true) -> Icons.Default.DirectionsRailway
        examId.equals("SSC", true) -> Icons.Default.Calculate
        else -> Icons.AutoMirrored.Filled.MenuBook
    }

    fun getTitleForExam(examId: String?): String = when {
        examId.equals("Navodaya", true) -> "नवोदय प्रवेश परीक्षा (Navodaya)"
        examId.equals("Sainik School", true) -> "सैनिक स्कूल परीक्षा (Sainik School)"
        examId.equals("Simultala", true) -> "सिमुलतला विद्यालय (Simultala)"
        examId.equals("Olympiad", true) -> "ओलंपियाड (Olympiad)"
        examId.equals("Railway", true) -> "रेलवे भर्ती बोर्ड (Railway CBT)"
        examId.equals("SSC", true) -> "एसएससी गणित (SSC Booster)"
        else -> "सभी संयुक्त टेस्ट (All Mixed)"
    }

    fun getSubjectMeta(subject: String): Triple<String, ImageVector, Color> = when {
        subject.contains("Math", true) -> Triple("गणित (Mathematics)", Icons.Default.Calculate, PrimaryBlue)
        subject.contains("Reasoning", true) || subject.contains("Mental", true) ->
            Triple("तर्कशक्ति व मानसिक योग्यता", Icons.Default.Psychology, AccentPurple)
        subject.contains("Science", true) -> Triple("सामान्य विज्ञान (Science)", Icons.Default.Biotech, AccentEmerald)
        subject.contains("English", true) -> Triple("अंग्रेजी (English Language)", Icons.Default.Language, Color(0xFF0284C7))
        subject.contains("Hindi", true) -> Triple("हिन्दी भाषा (Hindi)", Icons.Default.Translate, AccentOrange)
        subject.contains("General", true) || subject.contains("GK", true) ->
            Triple("सामान्य ज्ञान व अध्ययन (GK)", Icons.Default.Public, Color(0xFFB45309))
        else -> Triple(subject, Icons.Default.Quiz, PrimaryBlueLight)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedExamTopBar(
    title: String,
    subtitle: String,
    containerColor: Color,
    classBadge: String? = null,
    onBack: () -> Unit,
    onChangeClass: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (!classBadge.isNullOrBlank()) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFFDE047)
                        ) {
                            Text(
                                text = classBadge,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                                color = Color(0xFF0F172A),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                            )
                        }
                    }
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFDE047),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("nested_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            if (onChangeClass != null && !classBadge.isNullOrBlank()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    onClick = onChangeClass,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .minimumInteractiveComponentSize()
                        .testTag("change_class_button")
                ) {
                    Text(
                        text = "कक्षा बदलें",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = containerColor)
    )
}

@Composable
fun ExamTestCardItem(
    test: ExamTest,
    existingResult: ExamResult?,
    onStartClick: () -> Unit,
    onViewResultClick: () -> Unit,
    onLeaderboardClick: () -> Unit
) {
    val isAlreadyAttempted = existingResult != null

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("test_card_${test.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Top badges: Attempt status, Exam Class / SubType, Duration
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isAlreadyAttempted) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFEFF6FF)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Attempted",
                                    tint = PrimaryBlue,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "पूर्ण (Submitted)",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = PrimaryBlue
                                )
                            }
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = AccentEmerald.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "देना बाकी है",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = AccentEmerald,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    if (test.subType.isNotBlank() && test.testType == "FULL_LENGTH") {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = AccentPurple.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = test.subType,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = AccentPurple,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Duration",
                        tint = AccentOrange,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${test.durationMinutes} मिनट",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = Color(0xFF1E293B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = test.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF0F172A)
            )
            if (test.titleHindi.isNotBlank()) {
                Text(
                    text = test.titleHindi,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF475569)
                )
            }

            if (test.chapter.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFF1F5F9)
                ) {
                    Text(
                        text = "अध्याय: ${test.chapter}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                        color = PrimaryBlue,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF8FAFC))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "प्रश्न: ${test.totalQuestions}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF334155)
                )
                Text(
                    text = "सही: +${test.marksPerQuestion}",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = AccentEmerald
                )
                Text(
                    text = "नेगेटिव: -${test.negativeMarking}",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = Color(0xFFDC2626)
                )
            }

            if (isAlreadyAttempted && existingResult != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFEFF6FF),
                    border = BorderStroke(1.dp, Color(0xFFBFDBFE)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "आपका स्कोर: ${existingResult.totalMarks} / ${existingResult.maxMarks} अंक (${existingResult.percentage}%)",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = PrimaryBlue
                        )
                        Text(
                            text = "सही: ${existingResult.correctAnswers} | गलत: ${existingResult.incorrectAnswers} | शुद्धता: ${existingResult.accuracy}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF475569)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (isAlreadyAttempted) {
                    Button(
                        onClick = onViewResultClick,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "View Result",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("परिणाम व हल देखें", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                } else {
                    Button(
                        onClick = onStartClick,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AccentEmerald),
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("start_test_${test.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Start Test",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("टेस्ट शुरू करें (Start)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }

                OutlinedButton(
                    onClick = onLeaderboardClick,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.minimumInteractiveComponentSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Leaderboard,
                        contentDescription = "Leaderboard",
                        tint = AccentPurple,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("रैंक", color = AccentPurple, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
            }
        }
    }
}
