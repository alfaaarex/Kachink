package com.tasktracker.notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.tasktracker.MainActivity
import com.tasktracker.R
import com.tasktracker.data.model.Task
import com.tasktracker.data.repository.TaskRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class LockScreenNotificationService : Service() {
    
    @Inject
    lateinit var repository: TaskRepository
    
    private val serviceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var currentTaskId: Long? = null
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val taskId = intent?.getLongExtra("task_id", -1) ?: -1
        
        if (taskId != -1L) {
            currentTaskId = taskId
            serviceScope.launch {
                startForegroundWithTask(taskId)
                monitorTask(taskId)
            }
        }
        
        return START_STICKY
    }
    
    private suspend fun startForegroundWithTask(taskId: Long) {
        val task = repository.getTaskById(taskId)
        task?.let {
            val notification = createOngoingNotification(it)
            startForeground(NOTIFICATION_ID, notification)
        }
    }
    
    private suspend fun monitorTask(taskId: Long) {
        repository.getTaskByIdFlow(taskId).collect { task ->
            if (task == null || task.isCompleted) {
                stopSelf()
            } else {
                val notification = createOngoingNotification(task)
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
        }
    }
    
    private fun createOngoingNotification(task: Task): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("task_id", task.id)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val timeText = if (task.isScheduled) {
            "${task.startTime?.format(timeFormatter)} - ${task.endTime?.format(timeFormatter)}"
        } else {
            "In progress"
        }
        
        return NotificationCompat.Builder(this, TaskNotificationManager.CHANNEL_ID_ONGOING)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(task.title)
            .setContentText(timeText)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(if (task.description.isNotEmpty()) task.description else timeText)
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_PROGRESS)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
    
    companion object {
        private const val NOTIFICATION_ID = 1001
    }
}
