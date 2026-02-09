package com.tasktracker.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tasktracker.data.model.Task
import com.tasktracker.ui.theme.ExpressiveShapes
import com.tasktracker.ui.theme.Motion
import com.tasktracker.ui.theme.rememberHapticFeedback

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnscheduledTasksList(
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit,
    onAddTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(true) }
    val haptic = rememberHapticFeedback()
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = ExpressiveShapes.taskCard,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { 
                        isExpanded = !isExpanded
                        haptic.expandCollapse() // Haptic for expand/collapse
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Inbox,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Unscheduled Tasks",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${tasks.size} tasks",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                IconButton(onClick = { 
                    isExpanded = !isExpanded
                    haptic.lightTap()
                }) {
                    Icon(
                        imageVector = if (isExpanded) 
                            Icons.Default.ExpandLess 
                        else 
                            Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Tasks list
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = Motion.standard()) +
                        fadeIn(animationSpec = Motion.standard()),
                exit = shrinkVertically(animationSpec = Motion.standard()) +
                       fadeOut(animationSpec = Motion.standard())
            ) {
                Column(
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    if (tasks.isEmpty()) {
                        // Empty state
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "All tasks scheduled!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        tasks.forEach { task ->
                            UnscheduledTaskItem(
                                task = task,
                                onClick = { onTaskClick(task) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    
                    // Add task button
                    OutlinedButton(
                        onClick = {
                            onAddTask()
                            haptic.lightTap()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = ExpressiveShapes.labelChip
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add Unscheduled Task")
                    }
                }
            }
        }
    }
}

@Composable
private fun UnscheduledTaskItem(
    task: Task,
    onClick: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    val haptic = rememberHapticFeedback()
    
    Card(
        onClick = {
            onClick()
            haptic.lightTap()
        },
        modifier = Modifier.fillMaxWidth(),
        shape = ExpressiveShapes.labelChip,
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) 
                task.label.darkColor.copy(alpha = 0.3f)
            else 
                task.label.lightColor.copy(alpha = 0.2f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (task.description.isNotEmpty()) {
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }
            
            LabelChip(
                label = task.label,
                isSmall = true,
                contentColor = if (isDarkTheme) 
                    task.label.darkColor 
                else 
                    task.label.lightColor
            )
        }
    }
}
