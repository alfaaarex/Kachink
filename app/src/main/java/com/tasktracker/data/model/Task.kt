package com.tasktracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val title: String,
    
    val description: String = "",
    
    // Nullable for unscheduled tasks
    val startTime: LocalDateTime? = null,
    
    val endTime: LocalDateTime? = null,
    
    val label: TaskLabel = TaskLabel.CUSTOM,
    
    val priority: TaskPriority = TaskPriority.NONE,
    
    val isCompleted: Boolean = false,
    
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    
    // For recurring tasks (future enhancement)
    val isRecurring: Boolean = false,
    
    val recurringPattern: String? = null,
    
    // Notification settings
    val notificationEnabled: Boolean = true,
    
    val notificationMinutesBefore: Int = 5
) {
    val isScheduled: Boolean
        get() = startTime != null && endTime != null
    
    val durationMinutes: Int?
        get() = if (startTime != null && endTime != null) {
            java.time.Duration.between(startTime, endTime).toMinutes().toInt()
        } else null
}
