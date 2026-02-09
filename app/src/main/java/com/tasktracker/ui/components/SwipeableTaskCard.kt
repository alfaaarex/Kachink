package com.tasktracker.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tasktracker.data.model.Task
import com.tasktracker.ui.theme.rememberHapticFeedback

/**
 * Swipeable task card with Material 3 Expressive gestures
 * Swipe right: Complete task
 * Swipe left: Delete task
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableTaskCard(
    task: Task,
    onComplete: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = rememberHapticFeedback()
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    // Swipe right - Complete
                    onComplete()
                    haptic.success()
                    true
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    // Swipe left - Delete
                    onDelete()
                    haptic.heavyTap()
                    true
                }
                else -> false
            }
        },
        positionalThreshold = { it * 0.4f }
    )
    
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = {
            SwipeBackground(dismissState = dismissState)
        },
        content = {
            TaskCard(
                task = task,
                onClick = onClick
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeBackground(
    dismissState: SwipeToDismissBoxState
) {
    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            SwipeToDismissBoxValue.StartToEnd -> Color(0xFF66BB6A) // Green for complete
            SwipeToDismissBoxValue.EndToStart -> Color(0xFFEF5350) // Red for delete
            else -> Color.Transparent
        },
        animationSpec = tween(300),
        label = "swipeBackgroundColor"
    )
    
    val icon = when (dismissState.targetValue) {
        SwipeToDismissBoxValue.StartToEnd -> Icons.Default.CheckCircle
        SwipeToDismissBoxValue.EndToStart -> Icons.Default.Delete
        else -> null
    }
    
    val alignment = when (dismissState.targetValue) {
        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
        SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
        else -> Alignment.Center
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 24.dp),
        contentAlignment = alignment
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
