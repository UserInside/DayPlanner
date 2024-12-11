package com.example.simbirsoftdayplanner.domain

interface TaskListRepository {

    suspend fun getTaskList(): List<Task>

    fun addTask()

    fun editTask()

    fun deleteTask()
}