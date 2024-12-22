package com.example.simbirsoftdayplanner.domain

import java.util.Date

interface TaskRepository {

    suspend fun getTaskListByDate(date: Date): List<Task>

    suspend fun getTaskById(taskId: Int): Task?

    suspend fun addTask(task: Task)

    suspend fun editTask(task: Task)

    suspend fun deleteTask(taskId: Int)
}