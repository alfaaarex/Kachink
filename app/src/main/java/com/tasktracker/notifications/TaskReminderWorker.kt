package com.tasktracker.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TaskReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val notificationManager: TaskNotificationManager
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        val taskId = inputData.getLong("task_id", -1)
        val title = inputData.getString("task_title") ?: return Result.failure()
        val description = inputData.getString("task_description") ?: ""
        val startTime = inputData.getString("task_start_time") ?: ""
        
        if (taskId == -1L) return Result.failure()
        
        notificationManager.showTaskNotification(
            taskId = taskId,
            title = title,
            description = description,
            startTime = startTime
        )
        
        return Result.success()
    }
}
