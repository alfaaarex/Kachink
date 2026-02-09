package com.tasktracker.data.repository

import com.tasktracker.data.local.TaskDao
import com.tasktracker.data.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getTasksByDate(date: LocalDateTime): Flow<List<Task>> {
        return taskDao.getTasksByDate(date)
    }
    
    fun getUnscheduledTasks(): Flow<List<Task>> {
        return taskDao.getUnscheduledTasks()
    }
    
    fun getTasksByLabel(label: String): Flow<List<Task>> {
        return taskDao.getTasksByLabel(label)
    }
    
    suspend fun getTaskById(taskId: Long): Task? {
        return taskDao.getTaskById(taskId)
    }
    
    fun getTaskByIdFlow(taskId: Long): Flow<Task?> {
        return taskDao.getTaskByIdFlow(taskId)
    }
    
    fun getTasksInRange(startTime: LocalDateTime, endTime: LocalDateTime): Flow<List<Task>> {
        return taskDao.getTasksInRange(startTime, endTime)
    }
    
    suspend fun getUpcomingTasksWithNotifications(now: LocalDateTime): List<Task> {
        return taskDao.getUpcomingTasksWithNotifications(now)
    }
    
    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }
    
    suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task.copy(updatedAt = LocalDateTime.now()))
    }
    
    suspend fun insertTasks(tasks: List<Task>) {
        val now = LocalDateTime.now()
        taskDao.insertTasks(tasks.map { it.copy(updatedAt = now) })
    }
    
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.copy(updatedAt = LocalDateTime.now()))
    }
    
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
    
    suspend fun deleteTaskById(taskId: Long) {
        taskDao.deleteTaskById(taskId)
    }
    
    suspend fun toggleTaskCompletion(taskId: Long, isCompleted: Boolean) {
        taskDao.updateTaskCompletion(taskId, isCompleted, LocalDateTime.now())
    }
    
    suspend fun deleteOldCompletedTasks(daysOld: Int = 30) {
        val cutoffDate = LocalDateTime.now().minusDays(daysOld.toLong())
        taskDao.deleteCompletedTasksBefore(cutoffDate)
    }
}
