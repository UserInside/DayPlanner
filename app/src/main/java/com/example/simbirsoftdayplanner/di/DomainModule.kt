package com.example.simbirsoftdayplanner.di

import com.example.simbirsoftdayplanner.data.TaskDataRepositoryImpl
import com.example.simbirsoftdayplanner.data.db.TaskDao
import com.example.simbirsoftdayplanner.domain.TaskInteractor
import com.example.simbirsoftdayplanner.domain.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskDataRepositoryImpl(taskDao)
    }

    @Provides
    fun provideTaskInteractor(taskRepository: TaskRepository): TaskInteractor {
        return TaskInteractor(taskRepository)
    }
}