package com.example.tiramisuonlineshop.ui.theme.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun BrightenAnimationScreen(
    onAnimationEnd: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    var startFadeOut by remember { mutableStateOf(false) }

    // Animate circle radius
    val radius by animateFloatAsState(
        targetValue = when{startFadeOut -> 500f
        startAnimation -> 2000f
        else -> 1f
        }, // expand outward
        animationSpec = androidx.compose.animation.core.tween(
            durationMillis = if (startFadeOut) 800 else 2000
        ),
        finishedListener = {
            if (startFadeOut) {
                onAnimationEnd()
            } else {
                startFadeOut = true
            }
        }
    )

    // Animate color opacity
    val alpha by animateFloatAsState(
        targetValue = if (startFadeOut)  0f else 1f,

        animationSpec = androidx.compose.animation.core.tween(
            durationMillis = if (startFadeOut) 800 else 2000)

    )

    // Trigger animation once
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gradient = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFE6B0C9).copy(alpha = alpha),
                    Color(0xFF87CEEB).copy(alpha = alpha),
                    Color(0xFFD3D3D3).copy(alpha = alpha),
                    Color.Transparent
                ),
                center = Offset(size.width / 2, size.height / 2),
                radius = radius
            )

            drawCircle(
                brush = gradient, // mellow warm color
                radius = radius,
                center = Offset(size.width / 2, size.height / 2)
            )
        }
    }
}
