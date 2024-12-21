package com.example.simbirsoftdayplanner.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class TaskInteractor(
    private val repository: TaskRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getTaskList(): List<Task> {
        return repository.getTaskList()
    }

    suspend fun getTask(taskId: Int): Task? {
        return repository.getTaskById(taskId)
    }

    suspend fun addTask(task: Task) {
        coroutineScope {
            launch(dispatcher) {
                repository.addTask(task)
            }
        }
    }

    suspend fun editTask(task: Task) {
        coroutineScope {
            launch(dispatcher) {
                repository.editTask(task)
            }
        }
    }

    suspend fun deleteTask(taskId: Int) {
        coroutineScope {
            launch(dispatcher) {
                repository.deleteTask(taskId)
            }
        }
    }


}