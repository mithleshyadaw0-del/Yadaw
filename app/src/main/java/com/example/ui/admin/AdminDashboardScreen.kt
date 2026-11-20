package com.example.ui.admin

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.PublicOff
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExamResult
import com.example.data.model.ExamTest
import com.example.data.seed.SeedExamData
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SurfaceSoft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    viewModel: AdminViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tests by viewModel.tests.collectAsState()
    val allResults by viewModel.allResults.collectAsState()
    val opState by viewModel.opState.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("टेस्ट प्रबंधन", "नया टेस्ट बनाएं", "प्रश्न अपलोड", "छात्र परिणाम")

    var showCreateTestDialog by remember { mutableStateOf(false) }
    var showAddQuestionDialog by remember { mutableStateOf(false) }
    var showCsvImportDialog by remember { mutableStateOf(false) }
    var targetTestForQuestions by remember { mutableStateOf<ExamTest?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val clipboardManager = LocalClipboardManager.current
    val context = androidx.compose.ui.platform.LocalContext.current

    var excelContentInput by remember { mutableStateOf(SeedExamData.getSampleExcelTsvTemplate()) }
    var isAppendMode by remember { mutableStateOf(false) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val text = context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }
                if (!text.isNullOrBlank()) {
                    excelContentInput = text
                    Toast.makeText(context, "फाइल से प्रश्न डेटा सफलतापूर्वक लोड हुआ!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "चयनित फाइल खाली है।", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "फाइल पढ़ने में त्रुटि: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(opState) {
        when (val state = opState) {
            is AdminOpState.Success -> {
                snackbarHostState.showSnackbar(state.message)
                viewModel.clearOpState()
            }
            is AdminOpState.Error -> {
                snackbarHostState.showSnackbar("त्रुटि: ${state.message}")
                viewModel.clearOpState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "शिक्षक एवं एडमिन डैशबोर्ड",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Maths By Mithlesh Sir Control Panel",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFFDE047)
                        )
                    }
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
                    IconButton(onClick = { viewModel.seedStandardTests() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Seed Tests",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue)
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = SurfaceSoft,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = PrimaryBlue,
                edgePadding = 12.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> ManageTestsTab(
                    tests = tests,
                    onTogglePublish = { id, cur -> viewModel.togglePublish(id, cur) },
                    onDeleteTest = { id -> viewModel.deleteTest(id) },
                    onAddQuestion = { test ->
                        targetTestForQuestions = test
                        showAddQuestionDialog = true
                    },
                    onImportCsv = { test ->
                        targetTestForQuestions = test
                        showCsvImportDialog = true
                    },
                    onCreateNewClick = { selectedTabIndex = 1 }
                )
                1 -> CreateTestTab(
                    onCreateTest = { newTest ->
                        viewModel.createNewTest(newTest)
                        selectedTabIndex = 0
                    }
                )
                2 -> QuestionUploadTab(
                    tests = tests,
                    onSelectTestForAdd = { test ->
                        targetTestForQuestions = test
                        showAddQuestionDialog = true
                    },
                    onSelectTestForCsv = { test ->
                        targetTestForQuestions = test
                        showCsvImportDialog = true
                    }
                )
                3 -> StudentResultsTab(results = allResults)
            }
        }
    }

    // Add Single Question Dialog
    if (showAddQuestionDialog && targetTestForQuestions != null) {
        val test = targetTestForQuestions!!
        var qEn by remember { mutableStateOf("") }
        var qHi by remember { mutableStateOf("") }
        var opA by remember { mutableStateOf("") }
        var opB by remember { mutableStateOf("") }
        var opC by remember { mutableStateOf("") }
        var opD by remember { mutableStateOf("") }
        var correctChoice by remember { mutableStateOf("A") }
        var expEn by remember { mutableStateOf("") }
        var expHi by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddQuestionDialog = false },
            title = {
                Text(
                    text = "नया प्रश्न जोड़ें (${test.title.take(20)}...)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    OutlinedTextField(
                        value = qEn,
                        onValueChange = { qEn = it },
                        label = { Text("प्रश्न (English)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = qHi,
                        onValueChange = { qHi = it },
                        label = { Text("प्रश्न (हिन्दी)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = opA,
                        onValueChange = { opA = it },
                        label = { Text("विकल्प A") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = opB,
                        onValueChange = { opB = it },
                        label = { Text("विकल्प B") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = opC,
                        onValueChange = { opC = it },
                        label = { Text("विकल्प C") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = opD,
                        onValueChange = { opD = it },
                        label = { Text("विकल्प D") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text("सही उत्तर विकल्प:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("A", "B", "C", "D").forEach { choice ->
                            FilterChip(
                                selected = correctChoice == choice,
                                onClick = { correctChoice = choice },
                                label = { Text(choice) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = expEn,
                        onValueChange = { expEn = it },
                        label = { Text("हल / Explanation (English)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = expHi,
                        onValueChange = { expHi = it },
                        label = { Text("हल / व्याख्या (हिन्दी)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (qEn.isNotBlank() && opA.isNotBlank()) {
                            viewModel.addSingleQuestion(
                                testId = test.id,
                                qEn = qEn.trim(),
                                qHi = qHi.trim(),
                                opA = opA.trim(),
                                opB = opB.trim(),
                                opC = opC.trim(),
                                opD = opD.trim(),
                                correctOption = correctChoice,
                                expEn = expEn.trim(),
                                expHi = expHi.trim()
                            )
                            showAddQuestionDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("जोड़ें (Add Question)")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddQuestionDialog = false }) {
                    Text("रद्द करें")
                }
            }
        )
    }

    // Excel & CSV Bulk Upload Dialog
    if (showCsvImportDialog && targetTestForQuestions != null) {
        val test = targetTestForQuestions!!

        val recognizedCount = remember(excelContentInput) {
            val lines = excelContentInput.lines().map { it.trimEnd() }.filter { it.isNotBlank() }
            if (lines.isEmpty()) 0
            else {
                val first = lines.first()
                val isHeader = first.contains("Question", ignoreCase = true) ||
                        first.contains("Option", ignoreCase = true) ||
                        first.contains("प्रश्न", ignoreCase = true) ||
                        first.contains("Correct", ignoreCase = true)
                val rows = if (isHeader) lines.drop(1) else lines
                rows.count { line ->
                    val delimiter = if (line.contains('\t')) '\t' else if (line.contains(';') && !line.contains(',')) ';' else ','
                    line.split(delimiter).size >= 5
                }
            }
        }

        AlertDialog(
            onDismissRequest = { showCsvImportDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.TableChart,
                        contentDescription = "Excel",
                        tint = AccentEmerald,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "एक्सल / CSV से प्रश्न अपलोड करें",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF0F172A)
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "🎯 परीक्षा: ${test.title}",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryBlue
                            )
                            Text(
                                text = "श्रेणी: ${test.category} • वर्तमान कुल प्रश्न: ${test.totalQuestions}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF475569)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "अपलोड मोड चुनें (Upload Mode):",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF334155)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = !isAppendMode,
                            onClick = { isAppendMode = false },
                            label = { Text("पुराने बदलकर नए डालें (Replace)", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryBlue,
                                selectedLabelColor = Color.White
                            )
                        )
                        FilterChip(
                            selected = isAppendMode,
                            onClick = { isAppendMode = true },
                            label = { Text("पुराने में जोड़ें (Append)", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AccentEmerald,
                                selectedLabelColor = Color.White
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "त्वरित क्रियाएं (Quick Actions):",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF334155)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    // Action buttons grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { filePickerLauncher.launch("*/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.FolderOpen, contentDescription = "File", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("📁 फाइल चुनें", fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                val clipText = clipboardManager.getText()?.text
                                if (!clipText.isNullOrBlank()) {
                                    excelContentInput = clipText
                                    Toast.makeText(context, "क्लिपबोर्ड से डेटा पेस्ट हो गया!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "क्लिपबोर्ड खाली है। पहले एक्सेल से कॉपी करें।", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.ContentPaste, contentDescription = "Paste", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("📋 पेस्ट करें", fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(SeedExamData.getSampleExcelTsvTemplate()))
                                Toast.makeText(context, "एक्सल टेम्पलेट कॉपी हो गया! अब आप एक्सेल में पेस्ट कर सकते हैं।", Toast.LENGTH_LONG).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy Template", modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("टेम्पलेट कॉपी", fontSize = 11.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                excelContentInput = SeedExamData.getSampleExcelTsvTemplate()
                                Toast.makeText(context, "नमूना 5 प्रश्न लोड हो गए!", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Sample", modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("नमूना भरें", fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Recognition Status Banner
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (recognizedCount > 0) Color(0xFFDCFCE7) else Color(0xFFFEF3C7),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (recognizedCount > 0) Icons.Default.CheckCircle else Icons.Default.HelpOutline,
                                contentDescription = null,
                                tint = if (recognizedCount > 0) Color(0xFF16A34A) else Color(0xFFD97706),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (recognizedCount > 0)
                                    "पहचाने गए प्रश्न: $recognizedCount प्रश्न (अपलोड के लिए तैयार)"
                                else
                                    "कोई प्रश्न नहीं मिला। कृपया ऊपर दिए गए प्रारूप में डेटा भरें।",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (recognizedCount > 0) Color(0xFF15803D) else Color(0xFFB45309)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = excelContentInput,
                        onValueChange = { excelContentInput = it },
                        label = { Text("एक्सल / CSV डेटा (Excel Tab या Comma)") },
                        placeholder = { Text("Question_EN [Tab] Question_HI [Tab] Option_A ...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "नोट: MS Excel या Google Sheets से पंक्तियों (Rows) को सीधे कॉपी करके यहाँ 'पेस्ट करें' दबा सकते हैं।",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF64748B)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (excelContentInput.isNotBlank()) {
                            viewModel.importCsv(test.id, excelContentInput, append = isAppendMode)
                            showCsvImportDialog = false
                        }
                    },
                    enabled = recognizedCount > 0,
                    colors = ButtonDefaults.buttonColors(containerColor = AccentEmerald),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = if (recognizedCount > 0) "अपलोड करें ($recognizedCount प्रश्न)" else "अपलोड करें",
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showCsvImportDialog = false }) {
                    Text("रद्द करें")
                }
            }
        )
    }
}

@Composable
fun ManageTestsTab(
    tests: List<ExamTest>,
    onTogglePublish: (String, Boolean) -> Unit,
    onDeleteTest: (String) -> Unit,
    onAddQuestion: (ExamTest) -> Unit,
    onImportCsv: (ExamTest) -> Unit,
    onCreateNewClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "सभी परीक्षाएं (${tests.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF0F172A)
                )
                Button(
                    onClick = onCreateNewClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    modifier = Modifier.testTag("admin_create_test_button")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("नया टेस्ट")
                }
            }
        }

        if (tests.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("कोई टेस्ट उपलब्ध नहीं है। ऊपर दिए गए बटन से नया टेस्ट बनाएं।")
                    }
                }
            }
        } else {
            items(tests, key = { it.id }) { test ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = test.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF0F172A),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { onDeleteTest(test.id) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color(0xFFDC2626)
                                )
                            }
                        }

                        val classTag = if (test.examClass.isNotBlank()) " • ${test.examClass}" else ""
                        val typeTag = if (test.testType.isNotBlank()) " • ${test.testType}" else ""
                        Text(
                            text = "श्रेणी: ${test.category}$classTag$typeTag • समय: ${test.durationMinutes} मिनट • प्रश्न: ${test.totalQuestions}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF64748B)
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (test.isPublished) "प्रकाशित (Live)" else "अप्रकाशित (Draft)",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (test.isPublished) AccentEmerald else Color(0xFF94A3B8)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Switch(
                                    checked = test.isPublished,
                                    onCheckedChange = { onTogglePublish(test.id, test.isPublished) }
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                OutlinedButton(
                                    onClick = { onAddQuestion(test) },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("+ प्रश्न", fontSize = 12.sp)
                                }
                                Button(
                                    onClick = { onImportCsv(test) },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                                ) {
                                    Icon(imageVector = Icons.Default.FileUpload, contentDescription = "Excel", modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text("एक्सल / CSV", fontSize = 12.sp)
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
fun CreateTestTab(
    onCreateTest: (ExamTest) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var titleHindi by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Navodaya") }
    var examClass by remember { mutableStateOf("Class 6") }
    var testType by remember { mutableStateOf("FULL_LENGTH") }
    var subType by remember { mutableStateOf("Mock Test") }
    var subject by remember { mutableStateOf("Mathematics") }
    var chapter by remember { mutableStateOf("") }
    var examYear by remember { mutableStateOf("2025") }
    var durationMinutes by remember { mutableStateOf("20") }
    var totalQuestions by remember { mutableStateOf("0") }
    var marksPerQuestion by remember { mutableStateOf("1.0") }
    var negativeMarking by remember { mutableStateOf("0.0") }
    var allowRetakes by remember { mutableStateOf(false) }
    var showExplanations by remember { mutableStateOf(true) }
    var isPublished by remember { mutableStateOf(true) }

    val categories = listOf("Navodaya", "Sainik School", "Simultala", "Olympiad", "Railway", "SSC")
    val classOptions = when (category) {
        "Navodaya", "Sainik School", "Olympiad" -> listOf("Class 6", "Class 9")
        "Simultala" -> listOf("Class 6")
        else -> listOf("")
    }
    val testTypes = listOf(
        "FULL_LENGTH" to "Full Length",
        "CHAPTER_WISE" to "Chapter Wise",
        "SECTIONAL" to "Sectional",
        "SUBJECT_WISE" to "Subject Wise",
        "ADVANCED" to "Advanced",
        "PYQ" to "PYQ"
    )
    val subTypes = listOf("Mock Test", "Previous Year", "Model Paper", "Practice Test")
    val subjects = listOf(
        "All Subjects",
        "Mathematics",
        "Reasoning / Mental Ability",
        "Science",
        "English",
        "Hindi / Language",
        "General Knowledge"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "नई परीक्षा तैयार करें (Create New CBT Test)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = PrimaryBlue
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("परीक्षा का नाम (English Title)*") },
                    placeholder = { Text("उदा. JNVST Class 6 - Mock Test 02") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = titleHindi,
                    onValueChange = { titleHindi = it },
                    label = { Text("परीक्षा का नाम (हिन्दी शीर्षक)") },
                    placeholder = { Text("उदा. नवोदय कक्षा 6 - मॉक टेस्ट 02") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("परीक्षा चुनें (Examination):", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = category == cat,
                            onClick = {
                                category = cat
                                examClass = when (cat) {
                                    "Navodaya", "Sainik School", "Simultala", "Olympiad" ->
                                        if (examClass in listOf("Class 6", "Class 9") && cat != "Simultala") examClass else "Class 6"
                                    else -> ""
                                }
                            },
                            label = { Text(cat) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = PrimaryBlue, selectedLabelColor = Color.White)
                        )
                    }
                }

                if (classOptions.any { it.isNotBlank() }) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("कक्षा चुनें (Class):", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        classOptions.forEach { cls ->
                            FilterChip(
                                selected = examClass == cls,
                                onClick = { examClass = cls },
                                label = { Text(cls) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AccentEmerald, selectedLabelColor = Color.White)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text("टेस्ट प्रकार (Test Type):", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(testTypes) { (key, label) ->
                        FilterChip(
                            selected = testType == key,
                            onClick = { testType = key },
                            label = { Text(label, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AccentOrange, selectedLabelColor = Color.White)
                        )
                    }
                }

                if (testType == "FULL_LENGTH") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("पेपर प्रकार (Paper Format):", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(subTypes) { st ->
                            FilterChip(
                                selected = subType == st,
                                onClick = { subType = st },
                                label = { Text(st, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AccentPurple, selectedLabelColor = Color.White)
                            )
                        }
                    }
                }

                if (testType == "PYQ") {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = examYear,
                        onValueChange = { examYear = it },
                        label = { Text("परीक्षा वर्ष (Exam Year)") },
                        placeholder = { Text("उदा. 2025 या 2024") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text("विषय (Subject):", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(subjects) { subj ->
                        FilterChip(
                            selected = subject == subj,
                            onClick = { subject = subj },
                            label = { Text(subj, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = PrimaryBlue, selectedLabelColor = Color.White)
                        )
                    }
                }

                if (testType == "CHAPTER_WISE") {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = chapter,
                        onValueChange = { chapter = it },
                        label = { Text("अध्याय का नाम (Chapter Name)") },
                        placeholder = { Text("उदा. Number System (संख्या पद्धति)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = durationMinutes,
                        onValueChange = { durationMinutes = it },
                        label = { Text("समय (मिनट)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = totalQuestions,
                        onValueChange = { totalQuestions = it },
                        label = { Text("कुल प्रश्न") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = marksPerQuestion,
                        onValueChange = { marksPerQuestion = it },
                        label = { Text("अंक/प्रश्न (+)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = negativeMarking,
                        onValueChange = { negativeMarking = it },
                        label = { Text("नेगेटिव अंक (-)") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("तुरंत प्रकाशित करें (Publish Test):", style = MaterialTheme.typography.bodyMedium)
                    Switch(checked = isPublished, onCheckedChange = { isPublished = it })
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            val newTest = ExamTest(
                                id = "test_${System.currentTimeMillis()}",
                                title = title.trim(),
                                titleHindi = titleHindi.trim(),
                                subject = subject,
                                category = category,
                                examClass = examClass,
                                testType = testType,
                                subType = if (testType == "FULL_LENGTH") subType else if (testType == "PYQ") "Previous Year" else "",
                                examYear = if (testType == "PYQ") examYear.trim() else "",
                                chapter = if (testType == "CHAPTER_WISE") chapter.trim() else "",
                                durationMinutes = durationMinutes.toIntOrNull() ?: 20,
                                totalQuestions = totalQuestions.toIntOrNull() ?: 0,
                                marksPerQuestion = marksPerQuestion.toDoubleOrNull() ?: 1.0,
                                negativeMarking = negativeMarking.toDoubleOrNull() ?: 0.0,
                                isPublished = isPublished,
                                allowRetakes = allowRetakes,
                                showExplanations = showExplanations
                            )
                            onCreateTest(newTest)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("टेस्ट सुरक्षित करें (Save Test)", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun QuestionUploadTab(
    tests: List<ExamTest>,
    onSelectTestForAdd: (ExamTest) -> Unit,
    onSelectTestForCsv: (ExamTest) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "प्रश्न अपलोड गाइड (Question Upload System)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryBlue
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "आप किसी भी परीक्षा में मैन्युअल रूप से या CSV फाइल द्वारा 20+ प्रश्न एक साथ अपलोड कर सकते हैं। प्रश्न अंग्रेजी और हिन्दी दोनों भाषाओं में समर्थित हैं।",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF334155)
                    )
                }
            }
        }

        item {
            Text(
                text = "जिस परीक्षा में प्रश्न जोड़ना है, उसे चुनें:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF0F172A)
            )
        }

        items(tests, key = { it.id }) { test ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = test.title,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "${test.category} • वर्तमान प्रश्न: ${test.totalQuestions}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF64748B)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { onSelectTestForAdd(test) },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("+ प्रश्न जोड़ें", fontSize = 12.sp)
                        }
                        Button(
                            onClick = { onSelectTestForCsv(test) },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AccentEmerald)
                        ) {
                            Icon(imageVector = Icons.Default.FileUpload, contentDescription = "Excel", modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("एक्सल / CSV अपलोड", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentResultsTab(results: List<ExamResult>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = "छात्रों के परीक्षा परिणाम (${results.size})",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF0F172A)
            )
        }

        if (results.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("अभी तक किसी छात्र ने परीक्षा सबमिट नहीं की है।")
                    }
                }
            }
        } else {
            items(results) { res ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = res.studentName,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = "ID: ${res.studentId} • ${res.testTitle.take(24)}...",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF64748B)
                            )
                            Text(
                                text = "सही: ${res.correctAnswers} | गलत: ${res.incorrectAnswers} | सटीकता: ${res.accuracy}%",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF475569)
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${res.totalMarks} अंक",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                                color = AccentEmerald
                            )
                            Text(
                                text = "${res.percentage}%",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryBlue
                            )
                        }
                    }
                }
            }
        }
    }
}
