package com.example.simbirsoftdayplanner.di

import android.content.Context
import androidx.room.Room
import com.example.simbirsoftdayplanner.data.db.TaskDao
import com.example.simbirsoftdayplanner.data.db.TasksRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): TasksRoomDatabase =
        Room.databaseBuilder(context, TasksRoomDatabase::class.java, "tasks_database").build()

    @Provides
    @Singleton
    fun provideTaskDao(tasksRoomDatabase: TasksRoomDatabase): TaskDao {
        return tasksRoomDatabase.taskDao()
    }
}