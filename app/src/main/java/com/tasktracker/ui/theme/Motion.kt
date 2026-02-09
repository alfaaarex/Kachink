package com.tasktracker.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.ui.unit.dp

// Material 3 Expressive Motion - Spring-based animations
object Motion {
    // Spring specifications for natural, bouncy motion
    val spring = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )
    
    val springLow = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
    
    val springHigh = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessHigh
    )
    
    // Tween animations for specific use cases
    val fast = tween<Float>(
        durationMillis = 150,
        easing = FastOutSlowInEasing
    )
    
    val medium = tween<Float>(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
    
    val slow = tween<Float>(
        durationMillis = 500,
        easing = FastOutSlowInEasing
    )
    
    // Emphasized motion for important transitions
    val emphasized = tween<Float>(
        durationMillis = 400,
        easing = CubicBezierEasing(0.2f, 0f, 0f, 1f)
    )
    
    // Standard motion for most transitions
    val standard = tween<Float>(
        durationMillis = 300,
        easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)
    )
    
    // Enter/Exit animations
    val enterDuration = 300
    val exitDuration = 200
    
    // Gesture-responsive animation
    val gestureSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow
    )
}

// Animation durations
object AnimationDurations {
    const val SHORT = 150
    const val MEDIUM = 300
    const val LONG = 500
}

// Elevation values
object Elevation {
    val small = 2.dp
    val medium = 4.dp
    val large = 8.dp
    val extraLarge = 16.dp
}
