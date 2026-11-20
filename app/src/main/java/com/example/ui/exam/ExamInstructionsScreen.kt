package com.example.ui.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExamTest
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.CbtAnswered
import com.example.ui.theme.CbtNotAnswered
import com.example.ui.theme.CbtNotVisited
import com.example.ui.theme.CbtReview
import com.example.ui.theme.CbtReviewAnswered
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SurfaceSoft

import com.example.data.model.ExamResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamInstructionsScreen(
    test: ExamTest,
    existingResult: ExamResult? = null,
    onStartExam: () -> Unit,
    onViewResult: (ExamResult) -> Unit = {},
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedLanguage by remember { mutableStateOf("HI") } // Default Hindi for Indian competitive exams
    var isAgreed by remember { mutableStateOf(false) }

    val isAlreadyAttempted = existingResult != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "परीक्षा निर्देश (Exam Instructions)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // Language Switcher
                    Row(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Language",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (selectedLanguage == "EN") "English" else "हिन्दी",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue)
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (isAlreadyAttempted && existingResult != null) {
                        // Already Attempted Banner & Action
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFEF2F2),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🚫 आप यह परीक्षा पहले ही दे चुके हैं। एक छात्र केवल 1 बार ही परीक्षा दे सकता है। पुनः प्रयास की अनुमति नहीं है।",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFFDC2626)
                                )
                            }
                        }

                        Button(
                            onClick = { onViewResult(existingResult) },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("view_submitted_result_button")
                        ) {
                            Text(
                                text = "पिछला परिणाम व हल देखें (View Result)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = isAgreed,
                                onCheckedChange = { isAgreed = it },
                                colors = CheckboxDefaults.colors(checkedColor = PrimaryBlue),
                                modifier = Modifier.testTag("agree_checkbox")
                            )
                            Text(
                                text = if (selectedLanguage == "HI") {
                                    "मैंने सभी निर्देशों को पढ़ और समझ लिया है। मैं परीक्षा शुरू करने के लिए तैयार हूँ।"
                                } else {
                                    "I have read and understood all instructions. I am ready to begin the exam."
                                },
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                color = Color(0xFF1E293B)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        val hasQuestions = test.totalQuestions > 0

                        if (!hasQuestions) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFEF3C7),
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = "⚠️ इस परीक्षा में अभी कोई प्रश्न अपलोड नहीं किया गया है। मिथलेश सर द्वारा प्रश्न अपलोड करने के बाद यह परीक्षा शुरू होगी।",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFFB45309),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }

                        Button(
                            onClick = onStartExam,
                            enabled = isAgreed && hasQuestions,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AccentEmerald,
                                disabledContainerColor = Color(0xFFCBD5E1)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("start_exam_confirmed_button")
                        ) {
                            Text(
                                text = if (!hasQuestions) {
                                    "प्रश्नों की प्रतीक्षा है (0 प्रश्न)"
                                } else if (selectedLanguage == "HI") {
                                    "START EXAM (परीक्षा शुरू करें)"
                                } else {
                                    "START EXAM"
                                },
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
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
            // Test Overview Card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (selectedLanguage == "HI" && test.titleHindi.isNotBlank()) test.titleHindi else test.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF0F172A)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = PrimaryBlue.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = test.category,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryBlue,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        if (test.examClass.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = AccentEmerald.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = test.examClass,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = AccentEmerald,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                        if (test.subject.isNotBlank() && !test.subject.equals("All Subjects", true)) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = AccentOrange.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = test.subject,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = AccentOrange,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    if (existingResult != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = PrimaryBlue.copy(alpha = 0.08f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "📊 आपकी पूर्व परीक्षा का परिणाम:",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = PrimaryBlue
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "स्कोर: ${existingResult.totalMarks} / ${existingResult.maxMarks} (${existingResult.percentage}%) | सही: ${existingResult.correctAnswers} | गलत: ${existingResult.incorrectAnswers} | शुद्धता: ${existingResult.accuracy}%",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color(0xFF1E293B)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ExamMetaItem(label = "कुल प्रश्न", value = "${test.totalQuestions}")
                        ExamMetaItem(label = "कुल समय", value = "${test.durationMinutes} मिनट")
                        ExamMetaItem(label = "पूर्णांक", value = "${(test.totalQuestions * test.marksPerQuestion).toInt()} अंक")
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ExamMetaItem(label = "प्रत्येक सही उत्तर", value = "+${test.marksPerQuestion} अंक", valueColor = AccentEmerald)
                        ExamMetaItem(label = "नेगेटिव मार्किंग", value = "-${test.negativeMarking} अंक", valueColor = Color(0xFFDC2626))
                        ExamMetaItem(label = "परीक्षा प्रकार", value = "CBT (ऑनलाइन)")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Language Selection Toggle Card
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "निर्देश भाषा चुनें (Language):",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PrimaryBlue
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = selectedLanguage == "EN",
                            onClick = { selectedLanguage = "EN" },
                            label = { Text("English") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryBlue,
                                selectedLabelColor = Color.White
                            )
                        )
                        FilterChip(
                            selected = selectedLanguage == "HI",
                            onClick = { selectedLanguage = "HI" },
                            label = { Text("हिन्दी") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryBlue,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Important Instructions Card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Rule,
                            contentDescription = "Rules",
                            tint = AccentOrange
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (selectedLanguage == "HI") "महत्वपूर्ण परीक्षा निर्देश" else "Important Exam Instructions",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF0F172A)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val instructions = if (selectedLanguage == "HI") {
                        if (test.instructionsHi.isNotBlank()) test.instructionsHi else """
                            1. परीक्षा शुरू होने पर उलटी गिनती टाइमर (Countdown Timer) स्क्रीन पर दिखाई देगा।
                            2. प्रत्येक प्रश्न के लिए 4 विकल्प (A, B, C, D) दिए गए हैं।
                            3. आप स्क्रीन के शीर्ष पर स्थित भाषा बटन से किसी भी समय English या हिन्दी चुन सकते हैं।
                            4. प्रश्न का उत्तर देने के बाद 'Save & Next' बटन दबाएं।
                            5. यदि आप किसी प्रश्न पर बाद में विचार करना चाहते हैं तो 'Mark for Review' बटन का उपयोग करें।
                            6. अपने चुने हुए उत्तर को हटाने के लिए 'Clear Response' दबाएं।
                            7. टाइमर समाप्त होते ही परीक्षा स्वतः (Auto Submit) जमा हो जाएगी।
                        """.trimIndent()
                    } else {
                        if (test.instructionsEn.isNotBlank()) test.instructionsEn else """
                            1. A countdown timer will appear at the top right of the examination screen.
                            2. Each question has four MCQ options (A, B, C, D). Only one option is correct.
                            3. You can change language (English / Hindi) anytime during the exam.
                            4. Click 'Save & Next' to save your response and proceed to the next question.
                            5. Click 'Mark for Review' if you wish to review the question later.
                            6. Click 'Clear Response' to deselect a chosen option.
                            7. The exam will be automatically submitted when the timer reaches zero.
                        """.trimIndent()
                    }

                    Text(
                        text = instructions,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF334155),
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CBT Question Palette Legend Card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (selectedLanguage == "HI") "प्रश्न पैलेट संकेत (Question Palette Legend)" else "Question Palette Symbols",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF0F172A)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PaletteLegendItem(
                        color = CbtAnswered,
                        label = if (selectedLanguage == "HI") "उत्तर दिया गया (Answered)" else "Answered"
                    )
                    PaletteLegendItem(
                        color = CbtNotAnswered,
                        label = if (selectedLanguage == "HI") "उत्तर नहीं दिया गया (Not Answered / Visited)" else "Not Answered"
                    )
                    PaletteLegendItem(
                        color = CbtNotVisited,
                        label = if (selectedLanguage == "HI") "अभी तक देखा नहीं गया (Not Visited)" else "Not Visited"
                    )
                    PaletteLegendItem(
                        color = CbtReview,
                        label = if (selectedLanguage == "HI") "समीक्षा के लिए चिह्नित (Marked for Review)" else "Marked for Review"
                    )
                    PaletteLegendItem(
                        color = CbtReviewAnswered,
                        label = if (selectedLanguage == "HI") "उत्तर दिया व समीक्षा हेतु चिह्नित (Answered & Marked for Review)" else "Answered & Marked for Review",
                        hasBadge = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun ExamMetaItem(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF0F172A)
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF64748B)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = valueColor
        )
    }
}

@Composable
private fun PaletteLegendItem(
    color: Color,
    label: String,
    hasBadge: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            if (hasBadge) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(CbtAnswered)
                        .align(Alignment.BottomEnd)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = Color(0xFF1E293B)
        )
    }
}
