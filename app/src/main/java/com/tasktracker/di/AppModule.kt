package com.tasktracker.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.tasktracker.data.local.TaskDao
import com.tasktracker.data.local.TaskDatabase
import com.tasktracker.data.repository.TaskRepository
import com.tasktracker.ml.TaskClassifier
import com.tasktracker.notifications.TaskNotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    @Singleton
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }
    
    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao)
    }
    
    @Provides
    @Singleton
    fun provideTaskClassifier(
        @ApplicationContext context: Context
    ): TaskClassifier {
        return TaskClassifier(context)
    }
    
    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }
    
    @Provides
    @Singleton
    fun provideTaskNotificationManager(
        @ApplicationContext context: Context,
        workManager: WorkManager
    ): TaskNotificationManager {
        return TaskNotificationManager(context, workManager)
    }
}
