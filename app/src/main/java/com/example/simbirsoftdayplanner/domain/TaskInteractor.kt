package com.example.simbirsoftdayplanner.domain

import kotlinx.coroutines.CoroutineDispatcher

class TaskInteractor(
    private val repository: TaskRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getTaskList(): List<Task> {

        return repository.getTaskList()
    }

    suspend fun getTask(): Task {
        return repository.getTask()
    }

    fun addTask(task: Task) {
        repository.addTask(task)
    }

    fun editTask(task: Task) {
        repository.editTask(task)
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task.id)
    }


}