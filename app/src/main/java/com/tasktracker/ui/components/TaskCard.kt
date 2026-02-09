package com.tasktracker.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tasktracker.data.model.Task
import com.tasktracker.data.model.TaskLabel
import com.tasktracker.ui.theme.ExpressiveShapes
import com.tasktracker.ui.theme.Motion
import com.tasktracker.ui.theme.rememberHapticFeedback
import java.time.format.DateTimeFormatter

@Composable
fun TaskCard(
    task: Task,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val isDarkTheme = isSystemInDarkTheme()
    val haptic = rememberHapticFeedback()
    
    // Animated colors
    val backgroundColor by animateColorAsState(
        targetValue = if (task.isCompleted) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            if (isDarkTheme) task.label.darkColor else task.label.lightColor
        },
        animationSpec = Motion.standard,
        label = "backgroundColor"
    )
    
    val contentColor = if (task.isCompleted) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        Color.White
    }
    
    // Scale animation on press
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = Motion.spring,
        label = "pressScale"
    )
    
    Card(
        modifier = modifier
            .scale(scale)
            .clickable { 
                isPressed = true
                haptic.mediumTap() // Haptic feedback on tap
                onClick()
            },
        shape = if (isExpanded) ExpressiveShapes.taskCardExpanded else ExpressiveShapes.taskCard,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (task.isCompleted) 2.dp else 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Task title
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = contentColor,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                
                // Completion indicator
                if (task.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(start = 8.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Time and label row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Time
                if (task.isScheduled) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = contentColor.copy(alpha = 0.8f),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${task.startTime?.format(timeFormatter)} - ${task.endTime?.format(timeFormatter)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = contentColor.copy(alpha = 0.8f)
                        )
                    }
                }
                
                // Label chip
                LabelChip(
                    label = task.label,
                    isSmall = true,
                    contentColor = contentColor
                )
            }
            
            // Description (if expanded)
            if (isExpanded && task.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor.copy(alpha = 0.9f)
                )
            }
        }
    }
    
    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}

@Composable
fun LabelChip(
    label: TaskLabel,
    isSmall: Boolean = false,
    contentColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = ExpressiveShapes.labelChip,
        color = contentColor.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = if (isSmall) 6.dp else 12.dp,
                vertical = if (isSmall) 2.dp else 6.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (!isSmall) {
                Icon(
                    imageVector = when (label.icon) {
                        "school" -> Icons.Default.School
                        "person" -> Icons.Default.Person
                        "fitness_center" -> Icons.Default.FitnessCenter
                        "work" -> Icons.Default.Work
                        "favorite" -> Icons.Default.Favorite
                        "groups" -> Icons.Default.Group
                        else -> Icons.Default.Label
                    },
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = label.displayName,
                style = if (isSmall) 
                    MaterialTheme.typography.labelSmall 
                else 
                    MaterialTheme.typography.labelMedium,
                color = contentColor
            )
        }
    }
}

