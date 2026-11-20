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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.ExamResult
import com.example.data.model.ExamTest

@Composable
fun ClassSelectionScreen(
    examId: String,
    availableClasses: List<String>,
    tests: List<ExamTest>,
    results: List<ExamResult>,
    onSelectClass: (String) -> Unit,
    onBack: () -> Unit
) {
    val gradient = ExamVisualTheme.getGradientForExam(examId)
    val examTitle = ExamVisualTheme.getTitleForExam(examId)

    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = examTitle,
                subtitle = "Select Your Class (कक्षा चुनें)",
                examIcon = ExamVisualTheme.getIconForExam(examId),
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
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkExamColors.Card),
                    border = BorderStroke(1.dp, DarkExamColors.Border),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = DarkExamColors.Blue.copy(alpha = 0.18f),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = ExamVisualTheme.getIconForExam(examId),
                                    contentDescription = examTitle,
                                    tint = Color(0xFF93C5FD),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Choose Target Class (कक्षा चुनें)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = DarkExamColors.TextPrimary
                            )
                            Text(
                                text = "Mock Tests, PYQs & Study Notes are tailored separately for each class.",
                                style = MaterialTheme.typography.bodySmall,
                                color = DarkExamColors.TextSecondary
                            )
                        }
                    }
                }
            }

            items(availableClasses) { cls ->
                val classTests = tests.filter {
                    it.isPublished && it.category.equals(examId, true) && it.examClass.equals(cls, true)
                }
                val attempted = classTests.count { t -> results.any { it.testId == t.id } }
                val subjects = classTests.map { it.subject }
                    .filter { it.isNotBlank() && !it.equals("All Subjects", true) }
                    .distinct()
                val clsGradient = if (cls.contains("6")) gradient else listOf(Color(0xFF0F766E), Color(0xFF059669))

                Card(
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(1.dp, DarkExamColors.Border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("select_class_${cls.replace(" ", "_").lowercase()}")
                        .clickable { onSelectClass(cls) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.horizontalGradient(clsGradient))
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Class,
                                        contentDescription = cls,
                                        tint = Color(0xFFFDE047),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (cls == "Class 6") "Class 6 (कक्षा 6 प्रवेश)" else "Class 9 (कक्षा 9 प्रवेश)",
                                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                if (subjects.isNotEmpty()) {
                                    Text(
                                        text = "Subjects: ${subjects.joinToString(", ")}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White.copy(alpha = 0.22f)
                                ) {
                                    Text(
                                        text = "${classTests.size} Tests Available • $attempted Attempted",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.22f),
                                modifier = Modifier.size(42.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = "Open $cls",
                                        tint = Color.White
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

@Composable
fun FullLengthTestsScreen(
    examId: String,
    selectedClass: String?,
    tests: List<ExamTest>,
    results: List<ExamResult>,
    subTypeFilter: String,
    searchQuery: String,
    onSubTypeSelect: (String) -> Unit,
    onSearchChange: (String) -> Unit,
    onBack: () -> Unit,
    onStartTest: (ExamTest) -> Unit,
    onViewResult: (ExamResult) -> Unit,
    onViewLeaderboard: (String) -> Unit
) {
    val subTypes = listOf("All", "Mock Test", "Previous Year", "Model Paper", "Practice Test")
    val filtered = tests.filter { t ->
        val matchesSubType = subTypeFilter == "All" || t.subType.equals(subTypeFilter, ignoreCase = true)
        val matchesQuery = searchQuery.isBlank() ||
            t.title.contains(searchQuery, ignoreCase = true) ||
            t.titleHindi.contains(searchQuery, ignoreCase = true)
        matchesSubType && matchesQuery
    }

    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = "Full Length Tests",
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
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(subTypes) { st ->
                        val label = if (st == "All") "All (${tests.size})" else st
                        FilterChip(
                            selected = subTypeFilter == st,
                            onClick = { onSubTypeSelect(st) },
                            label = { Text(label) },
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

            item {
                DarkSearchField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = "Search full length mock tests..."
                )
            }

            if (filtered.isEmpty()) {
                item { DarkEmptyTestsCard("इस श्रेणी में कोई टेस्ट उपलब्ध नहीं है") }
            } else {
                items(filtered, key = { it.id }) { test ->
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
fun CategoryTestsListScreen(
    screenTitle: String,
    examId: String,
    selectedClass: String?,
    tests: List<ExamTest>,
    results: List<ExamResult>,
    searchQuery: String,
    filterChips: List<String> = emptyList(),
    activeFilter: String = "All",
    onFilterSelect: ((String) -> Unit)? = null,
    onSearchChange: (String) -> Unit,
    onBack: () -> Unit,
    onStartTest: (ExamTest) -> Unit,
    onViewResult: (ExamResult) -> Unit,
    onViewLeaderboard: (String) -> Unit
) {
    val filtered = tests.filter { t ->
        searchQuery.isBlank() ||
            t.title.contains(searchQuery, ignoreCase = true) ||
            t.titleHindi.contains(searchQuery, ignoreCase = true) ||
            t.examYear.contains(searchQuery, ignoreCase = true) ||
            t.subject.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            DarkNestedTopBar(
                title = screenTitle,
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
            if (filterChips.isNotEmpty() && onFilterSelect != null) {
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(filterChips) { chip ->
                            FilterChip(
                                selected = activeFilter.equals(chip, ignoreCase = true),
                                onClick = { onFilterSelect(chip) },
                                label = { Text(chip) },
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
            }

            item {
                DarkSearchField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = "Search tests..."
                )
            }

            if (filtered.isEmpty()) {
                item { DarkEmptyTestsCard("इस श्रेणी में अभी कोई टेस्ट उपलब्ध नहीं है") }
            } else {
                items(filtered, key = { it.id }) { test ->
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
fun DarkSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = DarkExamColors.TextMuted) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search", tint = DarkExamColors.Blue)
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = DarkExamColors.TextPrimary,
            unfocusedTextColor = DarkExamColors.TextPrimary,
            focusedContainerColor = DarkExamColors.Card,
            unfocusedContainerColor = DarkExamColors.Card,
            focusedBorderColor = DarkExamColors.Blue,
            unfocusedBorderColor = DarkExamColors.Border
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
