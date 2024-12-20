package com.example.simbirsoftdayplanner.di

import android.content.Context
import com.example.simbirsoftdayplanner.data.TaskRepositoryImpl
import com.example.simbirsoftdayplanner.data.db.TaskDao
import com.example.simbirsoftdayplanner.data.db.TasksRoomDatabase
import com.example.simbirsoftdayplanner.domain.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): TasksRoomDatabase {
        return TasksRoomDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideTaskDao(tasksRoomDatabase: TasksRoomDatabase): TaskDao {
        return tasksRoomDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository{
        return TaskRepositoryImpl(taskDao)
    }

}