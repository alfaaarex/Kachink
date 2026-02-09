package com.tasktracker.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasktracker.data.model.Task
import com.tasktracker.data.model.TaskLabel
import com.tasktracker.ui.theme.ExpressiveShapes
import com.tasktracker.ui.theme.rememberHapticFeedback
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorDialog(
    task: Task?,
    suggestedLabel: TaskLabel?,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    val haptic = rememberHapticFeedback()
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var selectedLabel by remember { mutableStateOf(task?.label ?: suggestedLabel ?: TaskLabel.CUSTOM) }
    var selectedPriority by remember { mutableStateOf(task?.priority ?: com.tasktracker.data.model.TaskPriority.NONE) }
    var isScheduled by remember { mutableStateOf(task?.isScheduled ?: false) }
    var startTime by remember { mutableStateOf(task?.startTime?.toLocalTime() ?: LocalTime.now()) }
    var endTime by remember { mutableStateOf(task?.endTime?.toLocalTime() ?: LocalTime.now().plusHours(1)) }
    var showTimePicker by remember { mutableStateOf(false) }
    var editingStartTime by remember { mutableStateOf(true) }
    
    val isValid = title.isNotBlank()
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = ExpressiveShapes.bottomSheet,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Text(
                text = if (task == null) "New Task" else "Edit Task",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Title input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Task Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = ExpressiveShapes.labelChip
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Description input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                shape = ExpressiveShapes.labelChip
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Label selection
            Text(
                text = "Category",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskLabel.values().take(4).forEach { label ->
                    FilterChip(
                        selected = selectedLabel == label,
                        onClick = { 
                            selectedLabel = label
                            haptic.selectionChange()
                        },
                        label = { Text(label.displayName) },
                        leadingIcon = {
                            Icon(
                                imageVector = when (label.icon) {
                                    "school" -> Icons.Default.School
                                    "person" -> Icons.Default.Person
                                    "fitness_center" -> Icons.Default.FitnessCenter
                                    "work" -> Icons.Default.Work
                                    else -> Icons.Default.Label
                                },
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskLabel.values().drop(4).forEach { label ->
                    FilterChip(
                        selected = selectedLabel == label,
                        onClick = { 
                            selectedLabel = label
                            haptic.selectionChange()
                        },
                        label = { Text(label.displayName) },
                        leadingIcon = {
                            Icon(
                                imageVector = when (label.icon) {
                                    "favorite" -> Icons.Default.Favorite
                                    "groups" -> Icons.Default.Group
                                    else -> Icons.Default.Label
                                },
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }
            }
            
            if (suggestedLabel != null && task == null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "✨ Suggested: ${suggestedLabel.displayName}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Priority selection
            Text(
                text = "Priority",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                com.tasktracker.data.model.TaskPriority.values().forEach { priority ->
                    FilterChip(
                        selected = selectedPriority == priority,
                        onClick = { 
                            selectedPriority = priority
                            haptic.selectionChange()
                        },
                        label = { Text(priority.displayName) },
                        leadingIcon = {
                            Icon(
                                imageVector = priority.icon,
                                contentDescription = null,
                                tint = priority.color,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Schedule toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Schedule this task",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Switch(
                    checked = isScheduled,
                    onCheckedChange = { 
                        isScheduled = it
                        haptic.mediumTap()
                    }
                )
            }
            
            // Time pickers
            AnimatedVisibility(visible = isScheduled) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Start time
                        OutlinedButton(
                            onClick = {
                                editingStartTime = true
                                showTimePicker = true
                            },
                            modifier = Modifier.weight(1f),
                            shape = ExpressiveShapes.labelChip
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Start", style = MaterialTheme.typography.labelSmall)
                                Text(
                                    startTime.toString(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                        
                        // End time
                        OutlinedButton(
                            onClick = {
                                editingStartTime = false
                                showTimePicker = true
                            },
                            modifier = Modifier.weight(1f),
                            shape = ExpressiveShapes.labelChip
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("End", style = MaterialTheme.typography.labelSmall)
                                Text(
                                    endTime.toString(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (onDelete != null) {
                    OutlinedButton(
                        onClick = {
                            haptic.heavyTap()
                            onDelete()
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Delete")
                    }
                }
                
                Button(
                    onClick = {
                        haptic.success()
                        val now = LocalDateTime.now()
                        val newTask = Task(
                            id = task?.id ?: 0,
                            title = title,
                            description = description,
                            label = selectedLabel,
                            priority = selectedPriority,
                            startTime = if (isScheduled) now.with(startTime) else null,
                            endTime = if (isScheduled) now.with(endTime) else null,
                            isCompleted = task?.isCompleted ?: false,
                            createdAt = task?.createdAt ?: now
                        )
                        onSave(newTask)
                        onDismiss()
                    },
                    enabled = isValid,
                    modifier = Modifier.weight(1f),
                    shape = ExpressiveShapes.labelChip
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
