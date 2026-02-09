package com.tasktracker.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.tasktracker.MainActivity
import com.tasktracker.R
import com.tasktracker.data.model.Task
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskNotificationManager @Inject constructor(
    private val context: Context,
    private val workManager: WorkManager
) {
    companion object {
        const val CHANNEL_ID_HIGH = "task_reminders_high"
        const val CHANNEL_ID_DEFAULT = "task_reminders_default"
        const val CHANNEL_ID_ONGOING = "task_ongoing"
        
        const val NOTIFICATION_ID_OFFSET = 10000
    }
    
    init {
        createNotificationChannels()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val highPriorityChannel = NotificationChannel(
                CHANNEL_ID_HIGH,
                "Task Reminders (High Priority)",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Important task reminders"
                enableVibration(true)
                enableLights(true)
            }
            
            val defaultChannel = NotificationChannel(
                CHANNEL_ID_DEFAULT,
                "Task Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Regular task reminders"
            }
            
            val ongoingChannel = NotificationChannel(
                CHANNEL_ID_ONGOING,
                "Ongoing Tasks",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Currently active tasks"
                setShowBadge(false)
            }
            
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(highPriorityChannel)
            notificationManager.createNotificationChannel(defaultChannel)
            notificationManager.createNotificationChannel(ongoingChannel)
        }
    }
    
    fun scheduleTaskNotification(task: Task) {
        val startTime = task.startTime ?: return
        val now = LocalDateTime.now()
        
        if (startTime.isBefore(now)) return
        
        val delay = Duration.between(now, startTime.minusMinutes(task.notificationMinutesBefore.toLong()))
        
        if (delay.isNegative) return
        
        val workRequest = OneTimeWorkRequestBuilder<TaskReminderWorker>()
            .setInitialDelay(delay.toMillis(), TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    "task_id" to task.id,
                    "task_title" to task.title,
                    "task_description" to task.description,
                    "task_start_time" to startTime.toString()
                )
            )
            .addTag("task_${task.id}")
            .build()
        
        workManager.enqueueUniqueWork(
            "task_notification_${task.id}",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
    
    fun cancelTaskNotification(taskId: Long) {
        workManager.cancelAllWorkByTag("task_$taskId")
    }
    
    fun showTaskNotification(
        taskId: Long,
        title: String,
        description: String,
        startTime: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }
        
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("task_id", taskId)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            taskId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_DEFAULT)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText("Starting at $startTime")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(if (description.isNotEmpty()) description else "Tap to view task")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(
                R.drawable.ic_check,
                "Complete",
                createCompleteActionIntent(taskId)
            )
            .addAction(
                R.drawable.ic_snooze,
                "Snooze",
                createSnoozeActionIntent(taskId)
            )
            .build()
        
        NotificationManagerCompat.from(context).notify(
            NOTIFICATION_ID_OFFSET + taskId.toInt(),
            notification
        )
    }
    
    private fun createCompleteActionIntent(taskId: Long): PendingIntent {
        val intent = Intent(context, TaskActionReceiver::class.java).apply {
            action = "ACTION_COMPLETE_TASK"
            putExtra("task_id", taskId)
        }
        return PendingIntent.getBroadcast(
            context,
            taskId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
    
    private fun createSnoozeActionIntent(taskId: Long): PendingIntent {
        val intent = Intent(context, TaskActionReceiver::class.java).apply {
            action = "ACTION_SNOOZE_TASK"
            putExtra("task_id", taskId)
        }
        return PendingIntent.getBroadcast(
            context,
            taskId.toInt() + 1000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
