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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
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

object DarkExamColors {
    val Bg = Color(0xFF0F1216)
    val TopBar = Color(0xFF151921)
    val Card = Color(0xFF181D25)
    val CardElevated = Color(0xFF202631)
    val Border = Color(0xFF2B3340)
    val Divider = Color(0xFF262D38)
    val TextPrimary = Color(0xFFF8FAFC)
    val TextSecondary = Color(0xFF94A3B8)
    val TextMuted = Color(0xFF64748B)
    val Blue = Color(0xFF3B82F6)
    val BlueDeep = Color(0xFF2563EB)
    val Emerald = Color(0xFF10B981)
    val Amber = Color(0xFFF59E0B)
    val Purple = Color(0xFF8B5CF6)
    val Rose = Color(0xFFEF4444)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DarkNestedTopBar(
    title: String,
    subtitle: String,
    examIcon: ImageVector? = null,
    classBadge: String? = null,
    onBack: () -> Unit,
    onChangeClass: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (examIcon != null) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = DarkExamColors.Blue.copy(alpha = 0.16f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = examIcon,
                                contentDescription = title,
                                tint = DarkExamColors.Blue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Column(modifier = Modifier.weight(1f, fill = false)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = DarkExamColors.TextPrimary,
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
                                color = DarkExamColors.Blue.copy(alpha = 0.22f)
                            ) {
                                Text(
                                    text = classBadge,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF93C5FD),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                                )
                            }
                        }
                        if (subtitle.isNotBlank()) {
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = DarkExamColors.TextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
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
                    tint = DarkExamColors.TextPrimary
                )
            }
        },
        actions = {
            if (onChangeClass != null && !classBadge.isNullOrBlank()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = DarkExamColors.CardElevated,
                    border = BorderStroke(1.dp, DarkExamColors.Border),
                    onClick = onChangeClass,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .minimumInteractiveComponentSize()
                        .testTag("change_class_button")
                ) {
                    Text(
                        text = "Change Class",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF93C5FD),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkExamColors.TopBar)
    )
}

@Composable
fun DarkExamTestCardItem(
    test: ExamTest,
    existingResult: ExamResult?,
    onStartClick: () -> Unit,
    onViewResultClick: () -> Unit,
    onLeaderboardClick: () -> Unit
) {
    val isAttempted = existingResult != null
    val totalMarks = test.totalQuestions * test.marksPerQuestion
    val formattedMarks = if (totalMarks % 1.0 == 0.0) "${totalMarks.toInt()}" else String.format("%.1f", totalMarks)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
        border = BorderStroke(1.dp, DarkExamColors.Border),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("test_card_${test.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isAttempted) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = DarkExamColors.Blue.copy(alpha = 0.2f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Attempted",
                                    tint = Color(0xFF60A5FA),
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Attempted",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF93C5FD)
                                )
                            }
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = DarkExamColors.Emerald.copy(alpha = 0.16f)
                        ) {
                            Text(
                                text = if (test.isFree) "Free Test" else "Not Attempted",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = DarkExamColors.Emerald,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    if (test.examYear.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = DarkExamColors.Amber.copy(alpha = 0.16f)
                        ) {
                            Text(
                                text = "PYQ ${test.examYear}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFFFCD34D),
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                            )
                        }
                    } else if (test.subType.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = DarkExamColors.Purple.copy(alpha = 0.16f)
                        ) {
                            Text(
                                text = test.subType,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = Color(0xFFC4B5FD),
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Duration",
                        tint = DarkExamColors.Amber,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${test.durationMinutes} Mins",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = DarkExamColors.TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = test.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = DarkExamColors.TextPrimary
            )
            if (test.titleHindi.isNotBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = test.titleHindi,
                    style = MaterialTheme.typography.bodySmall,
                    color = DarkExamColors.TextSecondary
                )
            }

            if (test.chapter.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = DarkExamColors.CardElevated
                ) {
                    Text(
                        text = "Chapter: ${test.chapter}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                        color = Color(0xFF93C5FD),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Metadata Strip: Questions | Total Marks | Marking
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(DarkExamColors.CardElevated)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${test.totalQuestions} Questions",
                    style = MaterialTheme.typography.labelMedium,
                    color = DarkExamColors.TextPrimary
                )
                Text(
                    text = "$formattedMarks Marks",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = DarkExamColors.Emerald
                )
                Text(
                    text = if (test.negativeMarking > 0) "-${test.negativeMarking} Neg" else "No Negative",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (test.negativeMarking > 0) Color(0xFFFCA5A5) else DarkExamColors.TextSecondary
                )
            }

            if (isAttempted && existingResult != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = DarkExamColors.Blue.copy(alpha = 0.12f),
                    border = BorderStroke(1.dp, DarkExamColors.Blue.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Score: ${existingResult.totalMarks} / ${existingResult.maxMarks} (${existingResult.percentage}%)",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF93C5FD)
                        )
                        Text(
                            text = "Correct: ${existingResult.correctAnswers} • Incorrect: ${existingResult.incorrectAnswers} • Accuracy: ${existingResult.accuracy}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = DarkExamColors.TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (isAttempted) {
                    Button(
                        onClick = onViewResultClick,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkExamColors.BlueDeep),
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
                        Text("View Result", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                } else {
                    Button(
                        onClick = onStartClick,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkExamColors.Emerald),
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("start_test_${test.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Start Test",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Start Test", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF042F2E))
                    }
                }

                OutlinedButton(
                    onClick = onLeaderboardClick,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, DarkExamColors.Border),
                    modifier = Modifier.minimumInteractiveComponentSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Leaderboard,
                        contentDescription = "Rank",
                        tint = Color(0xFFC4B5FD),
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Rank", color = Color(0xFFC4B5FD), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun DarkEmptyTestsCard(message: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
        border = BorderStroke(1.dp, DarkExamColors.Border),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = message,
                tint = DarkExamColors.TextMuted,
                modifier = Modifier.size(44.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
                color = DarkExamColors.TextSecondary
            )
        }
    }
}
