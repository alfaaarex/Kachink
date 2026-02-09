package com.tasktracker.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tasktracker.data.model.Task
import com.tasktracker.ui.components.EmptyState
import com.tasktracker.ui.theme.ExpressiveShapes
import com.tasktracker.ui.theme.rememberHapticFeedback
import com.tasktracker.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay

/**
 * Focus Mode - Distraction-free task completion
 * Material 3 Expressive: Immersive, calming, focused
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusModeScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onExit: () -> Unit
) {
    val haptic = rememberHapticFeedback()
    val uiState by viewModel.uiState.collectAsState()
    
    // Get current task (first incomplete scheduled task)
    val currentTask = uiState.scheduledTasks
        .filter { !it.isCompleted }
        .minByOrNull { it.startTime ?: java.time.LocalDateTime.MAX }
    
    // Pomodoro timer state
    var timeRemaining by remember { mutableStateOf(25 * 60) } // 25 minutes
    var isRunning by remember { mutableStateOf(false) }
    var isBreak by remember { mutableStateOf(false) }
    
    // Timer effect
    LaunchedEffect(isRunning) {
        while (isRunning && timeRemaining > 0) {
            delay(1000)
            timeRemaining--
            
            if (timeRemaining == 0) {
                haptic.success()
                isBreak = !isBreak
                timeRemaining = if (isBreak) 5 * 60 else 25 * 60
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Exit button
            IconButton(
                onClick = {
                    onExit()
                    haptic.lightTap()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Close, "Exit focus mode")
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            if (currentTask != null) {
                // Current task
                Text(
                    text = if (isBreak) "Break Time" else "Focus Time",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = currentTask.title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(48.dp))
                
                // Timer
                Card(
                    modifier = Modifier.size(240.dp),
                    shape = ExpressiveShapes.fab,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        val minutes = timeRemaining / 60
                        val seconds = timeRemaining % 60
                        
                        Text(
                            text = String.format("%02d:%02d", minutes, seconds),
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))
                
                // Controls
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FilledTonalButton(
                        onClick = {
                            isRunning = !isRunning
                            haptic.mediumTap()
                        },
                        modifier = Modifier.size(80.dp),
                        shape = ExpressiveShapes.fab
                    ) {
                        Icon(
                            imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isRunning) "Pause" else "Start",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    
                    FilledTonalButton(
                        onClick = {
                            timeRemaining = if (isBreak) 5 * 60 else 25 * 60
                            isRunning = false
                            haptic.lightTap()
                        },
                        modifier = Modifier.size(80.dp),
                        shape = ExpressiveShapes.fab
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Complete task button
                Button(
                    onClick = {
                        viewModel.updateTask(currentTask.copy(isCompleted = true))
                        haptic.success()
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Icon(Icons.Default.CheckCircle, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Complete Task")
                }
            } else {
                // No tasks
                EmptyState(
                    icon = Icons.Default.CheckCircle,
                    title = "All Done!",
                    message = "No tasks to focus on right now",
                    actionText = "Exit Focus Mode",
                    onAction = onExit
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
