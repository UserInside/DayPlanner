package com.example.simbirsoftdayplanner.domain

import kotlinx.datetime.LocalDate

class TaskInteractor(
    private val repository: TaskRepository,
) {

    suspend fun getTaskListByDate(localDate: LocalDate): List<Task> {
        return repository.getTaskListByDate(localDate)
    }

    suspend fun getTaskById(taskId: Int): Task? {
        return repository.getTaskById(taskId)
    }

    suspend fun addTask(task: Task) {
        repository.addTask(task)
    }

    suspend fun editTask(task: Task) {
        repository.editTask(task)
    }

    suspend fun deleteTask(taskId: Int) {
        repository.deleteTask(taskId)
    }


}