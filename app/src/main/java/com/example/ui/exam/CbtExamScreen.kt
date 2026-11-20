package com.example.ui.exam

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.sp
import com.example.data.model.CbtStatus
import com.example.data.model.ExamTest
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.CbtAnswered
import com.example.ui.theme.CbtNotAnswered
import com.example.ui.theme.CbtNotVisited
import com.example.ui.theme.CbtReview
import com.example.ui.theme.CbtReviewAnswered
import com.example.ui.theme.Navy900
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SurfaceSoft

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CbtExamScreen(
    test: ExamTest,
    currentQuestionIndex: Int,
    language: String, // "EN" or "HI"
    remainingSeconds: Int,
    viewModel: ExamViewModel,
    modifier: Modifier = Modifier
) {
    val questions = viewModel.getQuestionsList()
    val currentQuestion = questions.getOrNull(currentQuestionIndex)

    var showSubmitDialog by remember { mutableStateOf(false) }
    var showPaletteSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Back press safety confirmation
    var showExitWarningDialog by remember { mutableStateOf(false) }
    BackHandler {
        showExitWarningDialog = true
    }

    // Timer formatted string MM:SS
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timerText = String.format("%02d:%02d", minutes, seconds)
    val isTimerLow = remainingSeconds < 180 // under 3 minutes

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = test.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            maxLines = 1
                        )
                        Text(
                            text = "प्रश्न ${currentQuestionIndex + 1} / ${questions.size}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFFDE047)
                        )
                    }
                },
                actions = {
                    // Language Switch Button
                    Button(
                        onClick = { viewModel.toggleLanguage() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.testTag("toggle_exam_language_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Language",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (language == "EN") "English" else "हिन्दी",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Question Palette Toggle Button
                    IconButton(
                        onClick = { showPaletteSheet = true },
                        modifier = Modifier.testTag("open_question_palette_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = "Question Palette",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue)
            )
        },
        bottomBar = {
            // CBT Bottom Navigation Action Bar
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 12.dp)
                ) {
                    // Row 1: Question Actions (Clear, Mark for Review)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.clearResponse() },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("clear_response_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = Color(0xFFDC2626),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (language == "HI") "उत्तर हटाएं" else "Clear",
                                color = Color(0xFFDC2626),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        Button(
                            onClick = { viewModel.markForReviewAndNext() },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AccentPurple),
                            modifier = Modifier.testTag("mark_for_review_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Review",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (language == "HI") "रिव्यू करें व अगला" else "Mark for Review",
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Row 2: Navigation & Final Submit
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Previous Button
                        OutlinedButton(
                            onClick = { viewModel.navigateToPrevious() },
                            enabled = currentQuestionIndex > 0,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("previous_question_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Previous",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (language == "HI") "पिछला" else "Previous",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        // Save & Next Button
                        Button(
                            onClick = { viewModel.saveAndNext() },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AccentEmerald),
                            modifier = Modifier
                                .weight(1.3f)
                                .testTag("save_and_next_button")
                        ) {
                            Text(
                                text = if (language == "HI") "Save & Next" else "Save & Next",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelMedium
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next",
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        // Submit Test Button
                        Button(
                            onClick = { showSubmitDialog = true },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            modifier = Modifier
                                .weight(1.1f)
                                .testTag("submit_test_dialog_button")
                        ) {
                            Text(
                                text = if (language == "HI") "सबमिट" else "Submit",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        },
        containerColor = SurfaceSoft,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Live Countdown Timer & Section Ribbon
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isTimerLow) Color(0xFFFEF2F2) else Color(0xFFEFF6FF)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isTimerLow) Color(0xFFFCA5A5) else Color(0xFFBFDBFE)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "अनुभाग: ${test.subject}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF1E293B)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = "Timer",
                            tint = if (isTimerLow) Color(0xFFDC2626) else PrimaryBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = timerText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            ),
                            color = if (isTimerLow) Color(0xFFDC2626) else PrimaryBlue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Current Question Display
            val currentQ = currentQuestion ?: questions.firstOrNull()
            if (currentQ != null) {
                val selectedOption = viewModel.getSelectedAnswer(currentQ.id)
                val qText = if (language == "HI" && currentQ.questionHi.isNotBlank()) {
                    currentQ.questionHi
                } else {
                    currentQ.questionEn.ifBlank { "Question ${currentQuestionIndex + 1}" }
                }

                val options = if (language == "HI" && currentQ.optionsHi.isNotEmpty()) {
                    currentQ.optionsHi
                } else if (currentQ.optionsEn.isNotEmpty()) {
                    currentQ.optionsEn
                } else {
                    listOf("A) Option A", "B) Option B", "C) Option C", "D) Option D")
                }

                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = PrimaryBlue.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    text = "Question ${currentQuestionIndex + 1} / ${questions.size.coerceAtLeast(1)}",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = PrimaryBlue,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = "+${test.marksPerQuestion} / -${test.negativeMarking} Marks",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF64748B)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = qText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 24.sp
                            ),
                            color = Color(0xFF0F172A)
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                        Spacer(modifier = Modifier.height(16.dp))

                        // Four MCQ Options
                        val optionLetters = listOf("A", "B", "C", "D")
                        options.forEachIndexed { idx, optText ->
                            val letter = optionLetters.getOrElse(idx) { "A" }
                            val isSelected = selectedOption == letter

                            OptionCard(
                                letter = letter,
                                text = optText,
                                isSelected = isSelected,
                                onClick = { viewModel.selectAnswer(letter) },
                                modifier = Modifier.testTag("option_${letter}_question_${currentQuestionIndex + 1}")
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = PrimaryBlue)
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "प्रश्न लोड हो रहे हैं...",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF0F172A)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "कृपया 1-2 सेकंड प्रतीक्षा करें या नीचे दिए बटन पर टैप करें।",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadTest(test) },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            Text("प्रश्न पुनः लोड करें (Reload Questions)")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Modal Bottom Sheet: Question Palette
    if (showPaletteSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPaletteSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "प्रश्न पैलेट (Question Palette)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF0F172A)
                    )
                    IconButton(onClick = { showPaletteSheet = false }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Status Counts Summary
                val stats = viewModel.getPaletteStats()
                PaletteStatRow(stats = stats)

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9))
                Spacer(modifier = Modifier.height(16.dp))

                // Number Grid (Clickable)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    questions.forEachIndexed { idx, q ->
                        val status = viewModel.getQuestionStatus(q.id)
                        val isCurrent = idx == currentQuestionIndex

                        PaletteBubble(
                            number = idx + 1,
                            status = status,
                            isCurrent = isCurrent,
                            onClick = {
                                viewModel.navigateToQuestion(idx)
                                showPaletteSheet = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Submit Confirmation Dialog
    if (showSubmitDialog) {
        val stats = viewModel.getPaletteStats()
        val answered = stats[CbtStatus.ANSWERED] ?: 0
        val answeredAndReview = stats[CbtStatus.ANSWERED_AND_MARKED_FOR_REVIEW] ?: 0
        val markedReview = stats[CbtStatus.MARKED_FOR_REVIEW] ?: 0
        val notAnswered = (stats[CbtStatus.NOT_ANSWERED] ?: 0) + (stats[CbtStatus.NOT_VISITED] ?: 0)

        AlertDialog(
            onDismissRequest = { showSubmitDialog = false },
            title = {
                Text(
                    text = "परीक्षा जमा करें? (Submit Test)",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "क्या आप निश्चित रूप से परीक्षा जमा करना चाहते हैं?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "• कुल प्रश्न: ${questions.size}", style = MaterialTheme.typography.bodySmall)
                    Text(text = "• उत्तर दिए गए: ${answered + answeredAndReview}", style = MaterialTheme.typography.bodySmall, color = AccentEmerald)
                    Text(text = "• समीक्षा के लिए: $markedReview", style = MaterialTheme.typography.bodySmall, color = AccentPurple)
                    Text(text = "• अनुत्तरित: $notAnswered", style = MaterialTheme.typography.bodySmall, color = Color(0xFFDC2626))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSubmitDialog = false
                        viewModel.submitExam()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentEmerald),
                    modifier = Modifier.testTag("confirm_submit_exam_button")
                ) {
                    Text("हां, जमा करें (Submit)")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitDialog = false }) {
                    Text("रद्द करें")
                }
            }
        )
    }

    // Exit Warning Dialog (Prevent accidental back press)
    if (showExitWarningDialog) {
        AlertDialog(
            onDismissRequest = { showExitWarningDialog = false },
            title = { Text("परीक्षा बीच में न छोड़ें!", fontWeight = FontWeight.Bold) },
            text = {
                Text("यदि आप बाहर जाते हैं, तो टाइमर चलता रहेगा। आप बाद में होम स्क्रीन से इसे फिर से शुरू कर सकते हैं।")
            },
            confirmButton = {
                TextButton(onClick = { showExitWarningDialog = false }) {
                    Text("परीक्षा जारी रखें")
                }
            }
        )
    }
}

@Composable
fun OptionCard(
    letter: String,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) PrimaryBlue.copy(alpha = 0.08f) else Color(0xFFF8FAFC)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) PrimaryBlue else Color(0xFFE2E8F0)
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) PrimaryBlue else Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (isSelected) Color.White else Color(0xFF475569)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                ),
                color = if (isSelected) PrimaryBlue else Color(0xFF1E293B),
                modifier = Modifier.weight(1f)
            )

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = PrimaryBlue)
            )
        }
    }
}

@Composable
fun PaletteStatRow(stats: Map<CbtStatus, Int>) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatPill(color = CbtAnswered, label = "उत्तर दिया", count = stats[CbtStatus.ANSWERED] ?: 0)
            StatPill(color = CbtNotAnswered, label = "उत्तर नहीं", count = stats[CbtStatus.NOT_ANSWERED] ?: 0)
            StatPill(color = CbtNotVisited, label = "देखा नहीं", count = stats[CbtStatus.NOT_VISITED] ?: 0)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatPill(color = CbtReview, label = "रिव्यू", count = stats[CbtStatus.MARKED_FOR_REVIEW] ?: 0)
            StatPill(color = CbtReviewAnswered, label = "उत्तर + रिव्यू", count = stats[CbtStatus.ANSWERED_AND_MARKED_FOR_REVIEW] ?: 0)
        }
    }
}

@Composable
private fun StatPill(color: Color, label: String, count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$label: $count",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF475569)
        )
    }
}

@Composable
fun PaletteBubble(
    number: Int,
    status: CbtStatus,
    isCurrent: Boolean,
    onClick: () -> Unit
) {
    val bgColor = when (status) {
        CbtStatus.ANSWERED -> CbtAnswered
        CbtStatus.NOT_ANSWERED -> CbtNotAnswered
        CbtStatus.NOT_VISITED -> CbtNotVisited
        CbtStatus.MARKED_FOR_REVIEW -> CbtReview
        CbtStatus.ANSWERED_AND_MARKED_FOR_REVIEW -> CbtReviewAnswered
    }

    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .then(
                if (isCurrent) Modifier.border(2.dp, Color(0xFFFDE047), RoundedCornerShape(8.dp)) else Modifier
            )
            .clickable { onClick() }
            .testTag("palette_number_$number"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$number",
            color = Color.White,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )

        if (status == CbtStatus.ANSWERED_AND_MARKED_FOR_REVIEW) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(CbtAnswered)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}
