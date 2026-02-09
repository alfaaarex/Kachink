package com.tasktracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasktracker.data.model.Task
import com.tasktracker.data.model.TaskLabel
import com.tasktracker.data.repository.TaskRepository
import com.tasktracker.ml.TaskClassifier
import com.tasktracker.notifications.TaskNotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

data class MainUiState(
    val scheduledTasks: List<Task> = emptyList(),
    val unscheduledTasks: List<Task> = emptyList(),
    val currentDate: LocalDateTime = LocalDateTime.now(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val taskClassifier: TaskClassifier,
    private val notificationManager: TaskNotificationManager
) : ViewModel() {
    
    private val _currentDate = MutableStateFlow(LocalDateTime.now())
    val currentDate: StateFlow<LocalDateTime> = _currentDate.asStateFlow()
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    init {
        loadTasks()
    }
    
    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                combine(
                    repository.getTasksByDate(_currentDate.value),
                    repository.getUnscheduledTasks()
                ) { scheduled, unscheduled ->
                    MainUiState(
                        scheduledTasks = scheduled.filter { !it.isCompleted },
                        unscheduledTasks = unscheduled.filter { !it.isCompleted },
                        currentDate = _currentDate.value,
                        isLoading = false
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
    
    fun setDate(date: LocalDateTime) {
        _currentDate.value = date
        loadTasks()
    }
    
    fun createTask(task: Task) {
        viewModelScope.launch {
            try {
                val taskId = repository.insertTask(task)
                
                // Schedule notification if task is scheduled and in the future
                if (task.isScheduled && task.startTime?.isAfter(LocalDateTime.now()) == true) {
                    notificationManager.scheduleTaskNotification(task.copy(id = taskId))
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
    
    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.updateTask(task)
                
                // Reschedule notification
                notificationManager.cancelTaskNotification(task.id)
                if (task.isScheduled && 
                    task.startTime?.isAfter(LocalDateTime.now()) == true &&
                    task.notificationEnabled) {
                    notificationManager.scheduleTaskNotification(task)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
    
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.deleteTask(task)
                notificationManager.cancelTaskNotification(task.id)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
    
    fun toggleTaskCompletion(taskId: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            try {
                repository.toggleTaskCompletion(taskId, isCompleted)
                if (isCompleted) {
                    notificationManager.cancelTaskNotification(taskId)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
    
    fun moveTask(task: Task, newStartTime: LocalTime) {
        viewModelScope.launch {
            try {
                val duration = task.durationMinutes ?: 60
                val newEndTime = newStartTime.plusMinutes(duration.toLong())
                
                val updatedTask = task.copy(
                    startTime = _currentDate.value.with(newStartTime),
                    endTime = _currentDate.value.with(newEndTime)
                )
                
                updateTask(updatedTask)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
    
    fun classifyTask(title: String, description: String): Pair<TaskLabel, Float> {
        return taskClassifier.classifyTask(title, description)
    }
    
    fun suggestLabels(title: String, description: String): List<Pair<TaskLabel, Float>> {
        return taskClassifier.suggestLabels(title, description)
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
