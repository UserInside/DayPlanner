package com.example.simbirsoftdayplanner.presentation.mainscreen

import androidx.lifecycle.ViewModel
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository

class MainViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    //livadata polei neskolko


    fun addTask(task: Task) {
        repository.addTask(task)
    }

    fun editTask(task: Task) {
        repository.editTask(task)
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task.id)
    }

    suspend fun getTasksList() : List<Task> {
        return repository.getTaskList()
    }
}