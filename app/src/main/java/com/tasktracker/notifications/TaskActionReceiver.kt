package com.tasktracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tasktracker.data.repository.TaskRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskActionReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var repository: TaskRepository
    
    @Inject
    lateinit var notificationManager: TaskNotificationManager
    
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra("task_id", -1)
        if (taskId == -1L) return
        
        when (intent.action) {
            "ACTION_COMPLETE_TASK" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.toggleTaskCompletion(taskId, true)
                    notificationManager.cancelTaskNotification(taskId)
                }
            }
            "ACTION_SNOOZE_TASK" -> {
                // Snooze for 10 minutes
                CoroutineScope(Dispatchers.IO).launch {
                    val task = repository.getTaskById(taskId)
                    task?.let {
                        notificationManager.scheduleTaskNotification(it)
                    }
                }
            }
        }
    }
}
