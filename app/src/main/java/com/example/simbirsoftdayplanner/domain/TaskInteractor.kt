package com.example.simbirsoftdayplanner.domain

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Date

class TaskInteractor(
    private val repository: TaskRepository,
) {

    suspend fun getTaskListByDate(date: Date): List<Task> {
        return repository.getTaskListByDate(date)
    }

    suspend fun getTaskById(taskId: Int): Task? {
        return repository.getTaskById(taskId)
    }

    suspend fun addTask(task: Task) {
        coroutineScope {
            launch {
                repository.addTask(task)
            }
        }
    }

    suspend fun editTask(task: Task) {
        coroutineScope {
            launch {
                repository.editTask(task)
            }
        }
    }

    suspend fun deleteTask(taskId: Int) {
        coroutineScope {
            launch {
                repository.deleteTask(taskId)
            }
        }
    }


}