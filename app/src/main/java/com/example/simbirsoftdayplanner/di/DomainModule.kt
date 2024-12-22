package com.example.simbirsoftdayplanner.di

import com.example.simbirsoftdayplanner.data.TaskRepositoryImpl
import com.example.simbirsoftdayplanner.data.db.TaskDao
import com.example.simbirsoftdayplanner.domain.TaskInteractor
import com.example.simbirsoftdayplanner.domain.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

    @Provides
    fun provideTaskInteractor(taskRepository: TaskRepository): TaskInteractor {
        return TaskInteractor(taskRepository)
    }
}