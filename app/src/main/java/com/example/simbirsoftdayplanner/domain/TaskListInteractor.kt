package com.example.simbirsoftdayplanner.domain

class TaskListInteractor(
    private val repository: TaskListRepository
) {

    suspend fun getTaskList(): List<Task> {
        return repository.getTaskList()
    }

}