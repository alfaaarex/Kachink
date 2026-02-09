package com.tasktracker.data.local

import androidx.room.*
import com.tasktracker.data.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface TaskDao {
    
    @Query("SELECT * FROM tasks WHERE DATE(startTime) = DATE(:date) ORDER BY startTime ASC")
    fun getTasksByDate(date: LocalDateTime): Flow<List<Task>>
    
    @Query("SELECT * FROM tasks WHERE startTime IS NULL ORDER BY createdAt DESC")
    fun getUnscheduledTasks(): Flow<List<Task>>
    
    @Query("SELECT * FROM tasks WHERE label = :label ORDER BY startTime ASC, createdAt DESC")
    fun getTasksByLabel(label: String): Flow<List<Task>>
    
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): Task?
    
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskByIdFlow(taskId: Long): Flow<Task?>
    
    @Query("SELECT * FROM tasks WHERE isCompleted = 0 AND startTime >= :startTime AND startTime <= :endTime ORDER BY startTime ASC")
    fun getTasksInRange(startTime: LocalDateTime, endTime: LocalDateTime): Flow<List<Task>>
    
    @Query("SELECT * FROM tasks WHERE isCompleted = 0 AND notificationEnabled = 1 AND startTime > :now ORDER BY startTime ASC")
    suspend fun getUpcomingTasksWithNotifications(now: LocalDateTime): List<Task>
    
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<Task>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<Task>)
    
    @Update
    suspend fun updateTask(task: Task)
    
    @Delete
    suspend fun deleteTask(task: Task)
    
    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: Long)
    
    @Query("UPDATE tasks SET isCompleted = :isCompleted, updatedAt = :updatedAt WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: Long, isCompleted: Boolean, updatedAt: LocalDateTime)
    
    @Query("DELETE FROM tasks WHERE isCompleted = 1 AND updatedAt < :before")
    suspend fun deleteCompletedTasksBefore(before: LocalDateTime)
}
