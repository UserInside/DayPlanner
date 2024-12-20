package com.example.simbirsoftdayplanner.presentation.taskscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
//    val taskId: Int,
) : ViewModel() {

    var state = mutableStateOf(TaskScreenState(Task.mock()))
        private set

    init {
        viewModelScope.launch {
//            state.value.task = getTaskById(taskId)
        }
    }

    fun onEvent(event: TaskScreenEvent) {
        val task = Task.mock() //todo mock delete

        when (event) {
            is TaskScreenEvent.AddTaskEvent -> repository.addTask(state.value.task)
            is TaskScreenEvent.EditTaskEvent -> repository.editTask(state.value.task)
            is TaskScreenEvent.onNameChanged -> state.value.task.copy(name = event.text)
            is TaskScreenEvent.onDescriptionChanged -> state.value.task.copy(description = event.text)
        }
    }

    suspend fun getTaskById(taskId: Int): Task {
        return repository.getTaskById(taskId)
    }
}