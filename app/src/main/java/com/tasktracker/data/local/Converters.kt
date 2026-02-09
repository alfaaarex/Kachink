package com.tasktracker.data.local

import androidx.room.TypeConverter
import com.tasktracker.data.model.TaskLabel
import com.tasktracker.data.model.TaskPriority
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(formatter)
    }
    
    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, formatter) }
    }
    
    @TypeConverter
    fun fromTaskLabel(value: TaskLabel): String {
        return value.name
    }
    
    @TypeConverter
    fun toTaskLabel(value: String): TaskLabel {
        return TaskLabel.valueOf(value)
    }
    
    @TypeConverter
    fun fromTaskPriority(value: TaskPriority): String {
        return value.name
    }
    
    @TypeConverter
    fun toTaskPriority(value: String): TaskPriority {
        return TaskPriority.valueOf(value)
    }
}
