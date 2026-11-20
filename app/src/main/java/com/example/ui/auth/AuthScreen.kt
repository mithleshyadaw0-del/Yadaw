package com.example.ui.auth

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
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.PrimaryBlue

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    onAdminLoginSuccess: () -> Unit = {},
    onStudentLoginSuccess: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val credentialManager = remember { CredentialManager.create(context) }
    val uiState by authViewModel.uiState.collectAsState()

    val isAdmin by authViewModel.isAdmin.collectAsState()

    var showProfileDialog by remember { mutableStateOf(false) }
    var showDirectStudentDialog by remember { mutableStateOf(false) }
    var showAdminPinDialog by remember { mutableStateOf(false) }

    var studentNameInput by remember { mutableStateOf("") }
    var studentIdInput by remember { mutableStateOf("") }
    var studentClassInput by remember { mutableStateOf("Railway Group D / NTPC") }
    var directStudentError by remember { mutableStateOf("") }
    var profileSetupError by remember { mutableStateOf("") }
    var adminPinInput by remember { mutableStateOf("") }
    var adminPinError by remember { mutableStateOf("") }
    var isAdminPinVisible by remember { mutableStateOf(false) }

    val classOptions = listOf(
        "Railway Group D / NTPC",
        "SSC CGL / CHSL / MTS",
        "Navodaya Vidyalaya (JNVST)",
        "Sainik School (AISSEE)",
        "State Exams & General Maths"
    )

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthUiState.NeedsProfileSetup -> {
                showProfileDialog = true
                if (studentNameInput.isBlank()) {
                    studentNameInput = state.displayName
                }
            }
            is AuthUiState.Success -> {
                showProfileDialog = false
                showDirectStudentDialog = false
                if (state.profile.role == "admin" && isAdmin) {
                    onAdminLoginSuccess()
                } else {
                    onStudentLoginSuccess()
                }
            }
            else -> {}
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PrimaryBlue,
                        Color(0xFF1E293B)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Branding Header with Mithlesh Sir's Photo
            Surface(
                shape = CircleShape,
                color = Color.Black,
                shadowElevation = 8.dp,
                modifier = Modifier.size(92.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.mithlesh_sir_photo),
                        contentDescription = "Mithlesh Sir Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(92.dp)
                            .clip(CircleShape)
                            .border(3.dp, Color(0xFFFDE047), CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "MATHS BY MITHLESH SIR",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                ),
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = "ऑनलाइन गणित CBT परीक्षा पोर्टल",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFFDE047),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Railway • SSC • Navodaya • Sainik School",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Main Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "लॉगिन पोर्टल (Login Portal)",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF0F172A)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "परीक्षा देने के लिए गूगल से अथवा सीधे रोल नंबर से लॉगिन करें",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    if (uiState is AuthUiState.Loading) {
                        CircularProgressIndicator(
                            color = PrimaryBlue,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "कृपया प्रतीक्षा करें...",
                            style = MaterialTheme.typography.bodySmall,
                            color = PrimaryBlue
                        )
                    } else {
                        // 1. Google Sign-In Option
                        Button(
                            onClick = {
                                authViewModel.signInWithGoogle(context, credentialManager)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("google_signin_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Google",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Sign in with Google",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // 2. Direct Student Login (No Google account required!)
                        OutlinedButton(
                            onClick = { showDirectStudentDialog = true },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("direct_student_login_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Badge,
                                contentDescription = "Roll Number",
                                tint = PrimaryBlue
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "नाम व रोल नंबर से लॉगिन (Direct Student)",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = PrimaryBlue
                            )
                        }

                        if (uiState is AuthUiState.Error) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.errorContainer,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("auth_error_banner")
                            ) {
                                Text(
                                    text = (uiState as AuthUiState.Error).message,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                    HorizontalDivider(color = Color(0xFFE2E8F0))
                    Spacer(modifier = Modifier.height(16.dp))

                    // Teacher / Admin Section
                    Button(
                        onClick = { showAdminPinDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("admin_login_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Admin",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "शिक्षक / एडमिन लॉगिन (Admin Dashboard)",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    // Admin PIN / Access Dialog
    if (showAdminPinDialog) {
        androidx.compose.ui.window.Dialog(onDismissRequest = { showAdminPinDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Admin Security",
                            tint = AccentOrange,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "शिक्षक सत्यापन (Teacher Login)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF1E293B)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "केवल अधिकृत व्यवस्थापक / शिक्षक के लिए। कृपया अपना गोपनीय पासकोड दर्ज करें:",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = adminPinInput,
                        onValueChange = {
                            adminPinInput = it
                            adminPinError = ""
                        },
                        label = { Text("गुप्त एडमिन पासकोड (Admin Passcode)") },
                        placeholder = { Text("पासकोड दर्ज करें") },
                        visualTransformation = if (isAdminPinVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val icon = if (isAdminPinVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                            val desc = if (isAdminPinVisible) "पासकोड छुपाएं" else "पासकोड दिखाएं"
                            IconButton(onClick = { isAdminPinVisible = !isAdminPinVisible }) {
                                Icon(imageVector = icon, contentDescription = desc, tint = Color(0xFF64748B))
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_passcode_input")
                    )

                    if (adminPinError.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = adminPinError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { 
                            showAdminPinDialog = false
                            adminPinInput = ""
                            adminPinError = ""
                        }) {
                            Text("रद्द करें")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                val success = authViewModel.loginAsAdminWithPasscode(adminPinInput)
                                if (success) {
                                    showAdminPinDialog = false
                                    adminPinInput = ""
                                    adminPinError = ""
                                    onAdminLoginSuccess()
                                } else {
                                    adminPinError = "अमान्य पासकोड! केवल अधिकृत शिक्षक ही एडमिन डैशबोर्ड खोल सकते हैं।"
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                            modifier = Modifier.testTag("admin_confirm_login_button")
                        ) {
                            Text("डैशबोर्ड खोलें (Open)")
                        }
                    }
                }
            }
        }
    }

    // Direct Student Login Dialog (No Google sign-in required)
    if (showDirectStudentDialog) {
        androidx.compose.ui.window.Dialog(onDismissRequest = {
            showDirectStudentDialog = false
            directStudentError = ""
        }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp)
                ) {
                    Text(
                        text = "विद्यार्थी लॉगिन (Student Login)",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryBlue
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = studentNameInput,
                        onValueChange = {
                            studentNameInput = it
                            directStudentError = ""
                        },
                        label = { Text("विद्यार्थी का नाम (Name)*") },
                        placeholder = { Text("उदा. रोहन कुमार") },
                        isError = directStudentError.isNotBlank(),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("direct_student_name_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = studentIdInput,
                        onValueChange = { studentIdInput = it },
                        label = { Text("रोल नंबर / छात्र ID (Roll No.)") },
                        placeholder = { Text("उदा. MS-2026-01") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("direct_student_id_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("कक्षा / लक्ष्य परीक्षा:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    classOptions.take(3).forEach { opt ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            androidx.compose.material3.RadioButton(
                                selected = studentClassInput == opt,
                                onClick = { studentClassInput = opt }
                            )
                            Text(opt, style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    if (directStudentError.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = directStudentError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.testTag("direct_student_error_text")
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {
                            showDirectStudentDialog = false
                            directStudentError = ""
                        }) {
                            Text("रद्द करें")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (studentNameInput.isBlank()) {
                                    directStudentError = "कृपया विद्यार्थी का नाम दर्ज करें।"
                                } else {
                                    val ok = authViewModel.loginAsStudentDirect(
                                        name = studentNameInput.trim(),
                                        studentId = studentIdInput.trim().ifBlank { "MS-${(1000..9999).random()}" },
                                        studentClass = studentClassInput
                                    )
                                    if (ok) {
                                        directStudentError = ""
                                        showDirectStudentDialog = false
                                    } else {
                                        directStudentError = "कृपया मान्य नाम दर्ज करें।"
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            modifier = Modifier.testTag("direct_student_submit_button")
                        ) {
                            Text("प्रवेश करें")
                        }
                    }
                }
            }
        }
    }

    // Google Profile Setup Dialog
    if (showProfileDialog) {
        androidx.compose.ui.window.Dialog(onDismissRequest = { /* mandatory */ }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp)
                ) {
                    Text(
                        text = "प्रोफ़ाइल पूर्ण करें (Complete Profile)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryBlue
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = studentNameInput,
                        onValueChange = {
                            studentNameInput = it
                            profileSetupError = ""
                        },
                        label = { Text("नाम (Name)*") },
                        isError = profileSetupError.isNotBlank(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = studentIdInput,
                        onValueChange = { studentIdInput = it },
                        label = { Text("छात्र ID / रोल नंबर") },
                        placeholder = { Text("उदा. MS-2026-101") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (profileSetupError.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = profileSetupError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            if (studentNameInput.isBlank()) {
                                profileSetupError = "कृपया अपना नाम दर्ज करें।"
                            } else {
                                profileSetupError = ""
                                authViewModel.saveInitialProfile(
                                    name = studentNameInput.trim(),
                                    studentId = studentIdInput.trim().ifBlank { "MS-${(1000..9999).random()}" },
                                    studentClass = studentClassInput
                                )
                            }
                        },
                        enabled = uiState !is AuthUiState.Loading,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                    ) {
                        Text("आगे बढ़ें (Continue)")
                    }
                }
            }
        }
    }
}
