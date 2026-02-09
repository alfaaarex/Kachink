package com.tasktracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasktracker.data.model.TaskLabel
import com.tasktracker.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

data class TaskStats(
    val currentStreak: Int = 0,
    val tasksCompletedToday: Int = 0,
    val tasksCompletedWeek: Int = 0,
    val completionRate: Float = 0f,
    val categoryBreakdown: Map<TaskLabel, Int> = emptyMap(),
    val mostProductiveHour: String = "N/A"
)

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    
    private val _stats = MutableStateFlow(TaskStats())
    val stats: StateFlow<TaskStats> = _stats.asStateFlow()
    
    init {
        loadStats()
    }
    
    private fun loadStats() {
        viewModelScope.launch {
            repository.getAllTasks().collect { tasks ->
                val now = LocalDateTime.now()
                val today = now.toLocalDate()
                val weekAgo = today.minusDays(7)
                
                // Tasks completed today
                val completedToday = tasks.count { task ->
                    task.isCompleted && 
                    task.updatedAt.toLocalDate() == today
                }
                
                // Tasks completed this week
                val completedWeek = tasks.count { task ->
                    task.isCompleted && 
                    task.updatedAt.toLocalDate().isAfter(weekAgo)
                }
                
                // Completion rate
                val totalTasks = tasks.size
                val completedTasks = tasks.count { it.isCompleted }
                val completionRate = if (totalTasks > 0) {
                    completedTasks.toFloat() / totalTasks.toFloat()
                } else 0f
                
                // Category breakdown
                val categoryBreakdown = tasks
                    .filter { it.isCompleted }
                    .groupBy { it.label }
                    .mapValues { it.value.size }
                    .toSortedMap(compareByDescending { categoryBreakdown[it] ?: 0 })
                
                // Current streak
                val streak = calculateStreak(tasks)
                
                // Most productive hour
                val productiveHour = tasks
                    .filter { it.isCompleted && it.startTime != null }
                    .groupBy { it.startTime!!.hour }
                    .maxByOrNull { it.value.size }
                    ?.key
                    ?.let { hour -> String.format("%02d:00", hour) }
                    ?: "N/A"
                
                _stats.value = TaskStats(
                    currentStreak = streak,
                    tasksCompletedToday = completedToday,
                    tasksCompletedWeek = completedWeek,
                    completionRate = completionRate,
                    categoryBreakdown = categoryBreakdown,
                    mostProductiveHour = productiveHour
                )
            }
        }
    }
    
    private fun calculateStreak(tasks: List<com.tasktracker.data.model.Task>): Int {
        var streak = 0
        var currentDate = LocalDate.now()
        
        while (true) {
            val hasCompletedTask = tasks.any { task ->
                task.isCompleted && 
                task.updatedAt.toLocalDate() == currentDate
            }
            
            if (hasCompletedTask) {
                streak++
                currentDate = currentDate.minusDays(1)
            } else {
                break
            }
        }
        
        return streak
    }
}
