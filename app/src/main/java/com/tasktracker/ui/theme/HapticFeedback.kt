package com.tasktracker.ui.theme

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

/**
 * Haptic feedback manager for creating tactile, satisfying interactions
 * throughout the app. Provides various haptic patterns for different actions.
 */
class HapticFeedback(private val context: Context) {
    
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
    
    /**
     * Light tap - for button presses, chip selections
     */
    fun lightTap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator?.vibrate(
                VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(10)
        }
    }
    
    /**
     * Medium tap - for task completion, important actions
     */
    fun mediumTap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator?.vibrate(
                VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(20)
        }
    }
    
    /**
     * Heavy tap - for task deletion, significant changes
     */
    fun heavyTap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator?.vibrate(
                VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(30)
        }
    }
    
    /**
     * Double tap - for successful operations (save, complete)
     */
    fun doubleTap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timings = longArrayOf(0, 50, 50, 50)
            val amplitudes = intArrayOf(0, 100, 0, 150)
            vibrator?.vibrate(
                VibrationEffect.createWaveform(timings, amplitudes, -1)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(longArrayOf(0, 50, 50, 50), -1)
        }
    }
    
    /**
     * Success pattern - cheerful, satisfying feedback
     */
    fun success() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timings = longArrayOf(0, 30, 30, 30, 30, 50)
            val amplitudes = intArrayOf(0, 80, 0, 100, 0, 150)
            vibrator?.vibrate(
                VibrationEffect.createWaveform(timings, amplitudes, -1)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(longArrayOf(0, 30, 30, 30, 30, 50), -1)
        }
    }
    
    /**
     * Error pattern - distinct, attention-grabbing
     */
    fun error() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator?.vibrate(
                VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK)
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timings = longArrayOf(0, 50, 100, 50)
            val amplitudes = intArrayOf(0, 200, 0, 200)
            vibrator?.vibrate(
                VibrationEffect.createWaveform(timings, amplitudes, -1)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(longArrayOf(0, 50, 100, 50), -1)
        }
    }
    
    /**
     * Drag start - subtle feedback when starting to drag a task
     */
    fun dragStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(
                VibrationEffect.createOneShot(15, 100)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(15)
        }
    }
    
    /**
     * Drag snap - feedback when task snaps to a time slot
     */
    fun dragSnap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(
                VibrationEffect.createOneShot(25, 150)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(25)
        }
    }
    
    /**
     * Expand/collapse - smooth, satisfying feedback
     */
    fun expandCollapse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timings = longArrayOf(0, 20, 20, 30)
            val amplitudes = intArrayOf(0, 80, 0, 120)
            vibrator?.vibrate(
                VibrationEffect.createWaveform(timings, amplitudes, -1)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(longArrayOf(0, 20, 20, 30), -1)
        }
    }
    
    /**
     * Selection change - for switching between labels/categories
     */
    fun selectionChange() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator?.vibrate(
                VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(8)
        }
    }
}

/**
 * Composable function to get haptic feedback instance
 */
@Composable
fun rememberHapticFeedback(): HapticFeedback {
    val context = LocalContext.current
    return remember { HapticFeedback(context) }
}

/**
 * Extension function for View-based haptic feedback
 */
fun View.performHapticFeedback(feedbackType: Int = HapticFeedbackConstants.VIRTUAL_KEY) {
    performHapticFeedback(feedbackType, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
}
