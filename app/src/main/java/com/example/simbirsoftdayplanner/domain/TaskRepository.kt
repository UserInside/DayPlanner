package com.example.simbirsoftdayplanner.domain

interface TaskRepository {

    suspend fun getTaskList(): List<Task>

    suspend fun getTaskById(taskId: Int): Task

    fun addTask(task: Task)

    fun editTask(task: Task)

    fun deleteTask(taskId: Int)
}