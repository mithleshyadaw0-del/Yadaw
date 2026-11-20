package com.example.ui.analysis

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.ExamRepository
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SurfaceSoft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningAnalysisScreen(
    currentStudentUid: String,
    repository: ExamRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val results by repository.observeStudentResults(currentStudentUid).collectAsState(initial = emptyList())

    val totalAttempted = results.size
    val avgAccuracy = if (results.isNotEmpty()) results.map { it.accuracy }.average().toFloat() else 82.5f
    val avgScorePercent = if (results.isNotEmpty()) results.map { it.percentage }.average().toFloat() else 75.0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "सीखने का विश्लेषण (Learning Analysis)",
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue)
            )
        },
        containerColor = SurfaceSoft,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Speed & Accuracy Gauge Card
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = "Speed",
                                tint = AccentOrange
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "सटीकता एवं गति मीटर (Speed & Accuracy)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF0F172A)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Accuracy Progress
                        Text(
                            text = "औसत सटीकता (Average Accuracy): ${String.format("%.1f", avgAccuracy)}%",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { (avgAccuracy / 100f).coerceIn(0f, 1f) },
                            color = AccentEmerald,
                            trackColor = Color(0xFFE2E8F0),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Score Progress
                        Text(
                            text = "औसत प्राप्तांक प्रतिशत (Average Score %): ${String.format("%.1f", avgScorePercent)}%",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { (avgScorePercent / 100f).coerceIn(0f, 1f) },
                            color = PrimaryBlue,
                            trackColor = Color(0xFFE2E8F0),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        )
                    }
                }
            }

            // Topic Strength Card
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "अध्यायवार प्रदर्शन (Topic Strength)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF0F172A)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        TopicProgressItem(topic = "प्रतिशत एवं लाभ-हानि (Percentage & Profit-Loss)", percent = 88)
                        TopicProgressItem(topic = "समय और कार्य (Time & Work)", percent = 82)
                        TopicProgressItem(topic = "चाल, समय और दूरी (Speed & Train)", percent = 76)
                        TopicProgressItem(topic = "संख्या पद्धति एवं LCM-HCF (Number System)", percent = 92)
                        TopicProgressItem(topic = "ज्यामिति व क्षेत्रमिति (Geometry & Mensuration)", percent = 70)
                    }
                }
            }

            // Mithlesh Sir's Special Tips
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.TipsAndUpdates,
                                contentDescription = "Tips",
                                tint = AccentOrange
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "मिथलेश सर का विशेष सुझाव (Teacher's Tip)",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryBlue
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "1. नेगेटिव मार्किंग वाली परीक्षाओं (जैसे Railway & SSC) में जिन प्रश्नों पर पूर्ण विश्वास न हो, उन्हें अंतिम समय के लिए रिव्यू में रखें।\n2. कैलकुलेशन स्पीड बढ़ाने के लिए 1 से 30 तक के वर्ग और 1 से 20 तक के घन याद रखें।\n3. प्रत्येक मॉक टेस्ट देने के बाद गलत हुए प्रश्नों के हल की समीक्षा अवश्य करें।",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF334155),
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopicProgressItem(topic: String, percent: Int) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = topic,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = Color(0xFF1E293B)
            )
            Text(
                text = "$percent%",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = if (percent >= 80) AccentEmerald else AccentOrange
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { percent / 100f },
            color = if (percent >= 80) AccentEmerald else AccentOrange,
            trackColor = Color(0xFFF1F5F9),
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        )
    }
}
