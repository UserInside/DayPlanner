package com.example.simbirsoftdayplanner.domain

interface TaskRepository {

    suspend fun getTaskList(): List<Task>

    suspend fun getTask(): Task

    fun addTask(task: Task)

    fun editTask(task: Task)

    fun deleteTask(taskId: Int)
}