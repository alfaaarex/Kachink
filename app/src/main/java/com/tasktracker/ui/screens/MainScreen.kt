package com.tasktracker.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tasktracker.data.model.Task
import com.tasktracker.ui.components.*
import com.tasktracker.ui.theme.ExpressiveShapes
import com.tasktracker.ui.theme.rememberHapticFeedback
import com.tasktracker.ui.viewmodel.MainViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState()
    val haptic = rememberHapticFeedback()
    
    var showTaskEditor by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }
    var suggestedLabel by remember { mutableStateOf<com.tasktracker.data.model.TaskLabel?>(null) }
    
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = currentDate.format(dateFormatter),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "${uiState.scheduledTasks.size} scheduled tasks",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Open settings */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingTask = null
                    suggestedLabel = null
                    showTaskEditor = true
                    haptic.mediumTap()
                },
                shape = ExpressiveShapes.fab,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Timeline
                TimelineView(
                    tasks = uiState.scheduledTasks,
                    currentDate = currentDate,
                    onTaskClick = { task ->
                        editingTask = task
                        showTaskEditor = true
                    },
                    onTaskMove = { task, newTime ->
                        viewModel.moveTask(task, newTime)
                    },
                    modifier = Modifier.weight(1f)
                )
                
                // Unscheduled tasks
                UnscheduledTasksList(
                    tasks = uiState.unscheduledTasks,
                    onTaskClick = { task ->
                        editingTask = task
                        showTaskEditor = true
                    },
                    onAddTask = {
                        editingTask = null
                        suggestedLabel = null
                        showTaskEditor = true
                    }
                )
            }
            
            // Loading indicator
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            // Error snackbar
            uiState.error?.let { error ->
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    action = {
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text(error)
                }
            }
        }
    }
    
    // Task editor dialog
    if (showTaskEditor) {
        // Get ML suggestion for new tasks
        LaunchedEffect(editingTask) {
            if (editingTask == null) {
                suggestedLabel = null
            }
        }
        
        TaskEditorDialog(
            task = editingTask,
            suggestedLabel = suggestedLabel,
            onDismiss = {
                showTaskEditor = false
                editingTask = null
                suggestedLabel = null
            },
            onSave = { task ->
                // Get ML classification if it's a new task
                val finalTask = if (task.id == 0L && task.label == com.tasktracker.data.model.TaskLabel.CUSTOM) {
                    val (predictedLabel, confidence) = viewModel.classifyTask(task.title, task.description)
                    if (confidence > 0.15f) {
                        task.copy(label = predictedLabel)
                    } else {
                        task
                    }
                } else {
                    task
                }
                
                if (editingTask == null) {
                    viewModel.createTask(finalTask)
                } else {
                    viewModel.updateTask(finalTask)
                }
                
                showTaskEditor = false
                editingTask = null
                suggestedLabel = null
            },
            onDelete = if (editingTask != null) {
                { viewModel.deleteTask(editingTask!!) }
            } else null
        )
    }
}
