package com.example.ui.leaderboard

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExamTest
import com.example.data.model.LeaderboardEntry
import com.example.data.repository.ExamRepository
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SurfaceSoft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    initialTestId: String,
    repository: ExamRepository,
    currentStudentUid: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tests by repository.observeTests().collectAsState(initial = emptyList())
    var selectedTestId by remember(initialTestId) { mutableStateOf(initialTestId) }
    var expanded by remember { mutableStateOf(false) }

    val currentTest = tests.find { it.id == selectedTestId } ?: tests.firstOrNull()
    val activeTestId = currentTest?.id ?: selectedTestId

    val entries by repository.observeLeaderboard(activeTestId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "लाइव लीडरबोर्ड (Rankings)",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Test Selector Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = currentTest?.title ?: "Select Test",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("परीक्षा चुनें (Select Test)") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    tests.forEach { test ->
                        DropdownMenuItem(
                            text = { Text(test.title) },
                            onClick = {
                                selectedTestId = test.id
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Ranking Rules Card
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "रैंकिंग नियम: 1. अधिकतम अंक  2. उच्च सटीकता (Accuracy)  3. कम समय",
                    style = MaterialTheme.typography.labelSmall,
                    color = PrimaryBlue,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (entries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "No entries",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "इस टेस्ट के लिए अभी कोई रैंक उपलब्ध नहीं है।",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = "परीक्षा सबमिट करते ही आपका स्कोर यहां दिखेगा।",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(entries) { entry ->
                        val isCurrentUser = entry.studentUid == currentStudentUid
                        LeaderboardRowItem(entry = entry, isCurrentUser = isCurrentUser)
                    }
                }
            }
        }
    }
}

@Composable
fun LeaderboardRowItem(
    entry: LeaderboardEntry,
    isCurrentUser: Boolean
) {
    val rankBadgeColor = when (entry.rank) {
        1 -> Color(0xFFEAB308) // Gold
        2 -> Color(0xFF94A3B8) // Silver
        3 -> Color(0xFFB45309) // Bronze
        else -> PrimaryBlue
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrentUser) Color(0xFFEFF6FF) else Color.White
        ),
        border = if (isCurrentUser) androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank Number Bubble
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(rankBadgeColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${entry.rank}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Student Info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = entry.studentName,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF0F172A)
                    )
                    if (isCurrentUser) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = PrimaryBlue
                        ) {
                            Text(
                                text = "आप (You)",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
                Text(
                    text = "ID: ${entry.studentId} • ${entry.studentClass}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B)
                )
            }

            // Score and Time Taken
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${entry.score} अंक",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = AccentEmerald
                )
                Text(
                    text = "${entry.timeTakenSeconds / 60}m ${entry.timeTakenSeconds % 60}s • ${entry.accuracy}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}
