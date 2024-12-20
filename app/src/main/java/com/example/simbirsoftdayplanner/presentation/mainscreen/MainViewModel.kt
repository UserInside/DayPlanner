package com.example.simbirsoftdayplanner.presentation.mainscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    var state = mutableStateOf(MainScreenState())

    fun onEvent(event: MainScreenEvent){
        when (event) {
//            is MainScreenEvent.AddTaskEvent -> {
////                repository.addTask()
//            }
//            is MainScreenEvent.EditTaskEvent -> {
////                repository.editTask()
//            }
            is MainScreenEvent.DeleteTaskEvent -> {
//                repository.deleteTask()
            }
        }
    }

    suspend fun getTasksList(): List<Task> {
        return repository.getTaskList()
    }
}