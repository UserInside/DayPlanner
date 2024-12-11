package com.example.simbirsoftdayplanner.presentation.mainscreen

import androidx.lifecycle.ViewModel
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskListRepository

class MainViewModel(
    private val repository: TaskListRepository
) : ViewModel() {


    fun addTask() {
        repository.addTask()
    }

    fun editTask() {
        repository.editTask()
    }

    fun deleteTask() {
        repository.deleteTask()
    }

    suspend fun getTasksList() : List<Task> {
        return repository.getTaskList()
    }
}