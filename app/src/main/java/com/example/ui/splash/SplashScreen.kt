package com.example.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.PrimaryBlue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onAnimationFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale = remember { Animatable(0.3f) }
    val alpha = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val badgeOffsetY = remember { Animatable(30f) }

    // Infinite breathing glow animation
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val floatingFloat by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating"
    )

    val shimmerRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    LaunchedEffect(Unit) {
        // Logo scale bounce & fade in
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
        )
        badgeOffsetY.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
        textAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
        )

        // Hold splash screen for complete professional branding presentation
        delay(1400)
        onAnimationFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PrimaryBlue,
                        Color(0xFF0F172A),
                        Color(0xFF030712)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Floating Decorative Math Symbols in Background
        Box(modifier = Modifier.fillMaxSize()) {
            MathSymbol(text = "π", modifier = Modifier.align(Alignment.TopStart).padding(start = 40.dp, top = 80.dp), alpha = 0.20f)
            MathSymbol(text = "∑", modifier = Modifier.align(Alignment.TopEnd).padding(end = 45.dp, top = 100.dp), alpha = 0.22f)
            MathSymbol(text = "√x", modifier = Modifier.align(Alignment.CenterStart).padding(start = 30.dp), alpha = 0.18f)
            MathSymbol(text = "%", modifier = Modifier.align(Alignment.CenterEnd).padding(end = 35.dp), alpha = 0.25f)
            MathSymbol(text = "∫", modifier = Modifier.align(Alignment.BottomStart).padding(start = 50.dp, bottom = 120.dp), alpha = 0.20f)
            MathSymbol(text = "θ", modifier = Modifier.align(Alignment.BottomEnd).padding(end = 55.dp, bottom = 140.dp), alpha = 0.22f)
        }

        // Central Animated Logo & Branding Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Pulsing Glow behind badge
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .scale(scale.value * pulseScale)
                    .alpha(alpha.value)
            ) {
                // Outer Ambient Glow
                Surface(
                    shape = CircleShape,
                    color = AccentOrange.copy(alpha = 0.22f),
                    modifier = Modifier
                        .size(140.dp)
                        .blur(16.dp)
                ) {}

                // Middle Ring
                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.15f),
                    modifier = Modifier.size(115.dp)
                ) {}

                // Inner Photo with Golden Border
                Surface(
                    shape = CircleShape,
                    color = Color.Black,
                    shadowElevation = 14.dp,
                    modifier = Modifier.size(105.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.mithlesh_sir_photo),
                            contentDescription = "Mithlesh Sir Photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(105.dp)
                                .clip(CircleShape)
                                .border(3.dp, Color(0xFFFDE047), CircleShape)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Main App Title with Animation
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .alpha(textAlpha.value)
                    .scale(scale.value.coerceAtLeast(0.85f))
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFFDE047).copy(alpha = 0.18f),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "★ ऑनलाइन CBT परीक्षा पोर्टल ★",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFFFDE047),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = "MATHS BY MITHLESH SIR",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.2.sp
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "रेलवे • एसएससी • नवोदय • सैनिक स्कूल विशेष",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = Color(0xFFE2E8F0),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Sleek Animated Progress Indicator
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .alpha(textAlpha.value)
                    .fillMaxWidth(0.65f)
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp),
                    color = Color(0xFFFDE047),
                    trackColor = Color.White.copy(alpha = 0.2f),
                    strokeCap = StrokeCap.Round
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "परीक्षा पोर्टल प्रारंभ हो रहा है...",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}

@Composable
private fun MathSymbol(text: String, modifier: Modifier, alpha: Float) {
    Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        modifier = modifier.alpha(alpha)
    )
}
