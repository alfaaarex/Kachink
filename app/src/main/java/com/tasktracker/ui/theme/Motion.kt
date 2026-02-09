package com.tasktracker.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.ui.unit.dp

// Material 3 Expressive Motion - Spring-based animations
object Motion {
    // Spring specifications for natural, bouncy motion
    fun <T> spring() = spring<T>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )
    
    val springFloat = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )
    
    fun <T> springLow() = spring<T>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
    
    fun <T> springHigh() = spring<T>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessHigh
    )
    
    // Tween animations for specific use cases
    fun <T> fast() = tween<T>(
        durationMillis = 150,
        easing = FastOutSlowInEasing
    )
    
    fun <T> medium() = tween<T>(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
    
    fun <T> slow() = tween<T>(
        durationMillis = 500,
        easing = FastOutSlowInEasing
    )
    
    // Emphasized motion for important transitions
    fun <T> emphasized() = tween<T>(
        durationMillis = 400,
        easing = CubicBezierEasing(0.2f, 0f, 0f, 1f)
    )
    
    // Standard motion for most transitions
    fun <T> standard() = tween<T>(
        durationMillis = 300,
        easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)
    )
    
    // Enter/Exit animations
    const val enterDuration = 300
    const val exitDuration = 200
    
    // Gesture-responsive animation
    fun <T> gestureSpring() = spring<T>(
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
