package com.tasktracker.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Celebration animation with confetti for task completion
 * Material 3 Expressive: Playful, joyful, satisfying
 */
@Composable
fun TaskCompletionCelebration(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var confettiParticles by remember { mutableStateOf(generateConfetti()) }
    
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300)),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Confetti background
            ConfettiCanvas(particles = confettiParticles)
            
            // Success icon with bounce animation
            val scale by animateFloatAsState(
                targetValue = if (visible) 1f else 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "iconScale"
            )
            
            val rotation by animateFloatAsState(
                targetValue = if (visible) 360f else 0f,
                animationSpec = tween(600, easing = FastOutSlowInEasing),
                label = "iconRotation"
            )
            
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Task completed",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        rotationZ = rotation
                    }
            )
        }
    }
    
    LaunchedEffect(visible) {
        if (visible) {
            // Animate confetti
            repeat(60) {
                delay(16) // ~60 FPS
                confettiParticles = confettiParticles.map { it.update() }
            }
            delay(500)
            onDismiss()
        } else {
            confettiParticles = generateConfetti()
        }
    }
}

@Composable
private fun ConfettiCanvas(particles: List<ConfettiParticle>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach { particle ->
            drawCircle(
                color = particle.color,
                radius = particle.size,
                center = Offset(
                    x = particle.x * size.width,
                    y = particle.y * size.height
                ),
                alpha = particle.alpha
            )
        }
    }
}

private data class ConfettiParticle(
    var x: Float,
    var y: Float,
    val velocityX: Float,
    val velocityY: Float,
    val size: Float,
    val color: Color,
    var alpha: Float = 1f,
    var life: Int = 60
) {
    fun update(): ConfettiParticle {
        return copy(
            x = x + velocityX,
            y = y + velocityY,
            alpha = (life.toFloat() / 60f).coerceIn(0f, 1f),
            life = life - 1
        )
    }
}

private fun generateConfetti(): List<ConfettiParticle> {
    val colors = listOf(
        Color(0xFFEF5350), // Red
        Color(0xFF42A5F5), // Blue
        Color(0xFFFFCA28), // Yellow
        Color(0xFF66BB6A), // Green
        Color(0xFFAB47BC), // Purple
        Color(0xFFFF7043)  // Orange
    )
    
    return List(50) {
        val angle = Random.nextFloat() * 2 * Math.PI.toFloat()
        val speed = Random.nextFloat() * 0.01f + 0.005f
        
        ConfettiParticle(
            x = 0.5f,
            y = 0.5f,
            velocityX = cos(angle) * speed,
            velocityY = sin(angle) * speed,
            size = Random.nextFloat() * 8f + 4f,
            color = colors.random()
        )
    }
}
