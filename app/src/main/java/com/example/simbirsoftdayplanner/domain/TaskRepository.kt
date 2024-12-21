package com.example.simbirsoftdayplanner.domain

interface TaskRepository {

    suspend fun getTaskList(): List<Task>

    suspend fun getTaskById(taskId: Int): Task?

    suspend fun addTask(task: Task)

    suspend fun editTask(task: Task)

    suspend fun deleteTask(taskId: Int)
}