package com.tasktracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tasktracker.data.model.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    
    companion object {
        const val DATABASE_NAME = "task_tracker_db"
    }
}
