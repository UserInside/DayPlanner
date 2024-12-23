package com.example.simbirsoftdayplanner.domain

import kotlinx.datetime.LocalDate

interface TaskRepository {

    suspend fun getTaskListByDate(date: LocalDate): List<Task>

    suspend fun getTaskById(taskId: Int): Task?

    suspend fun addTask(task: Task)

    suspend fun editTask(task: Task)

    suspend fun deleteTask(taskId: Int)
}