package com.example.simbirsoftdayplanner.presentation.taskscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = TaskViewModelFactory::class)
class TaskViewModel @AssistedInject constructor(
    private val repository: TaskRepository,
    @Assisted val taskId: Int,
) : ViewModel() {

    var state by mutableStateOf(TaskScreenState.emptyMock()) //todo убрать мок

    init {
        viewModelScope.launch {
            state = getTaskById(taskId)
        }
    }

    fun onEvent(event: TaskScreenEvent) {
        when (event) {
            is TaskScreenEvent.AddTaskEvent -> {
                val task = Task(
                    name = state.name,
                    description = state.description,
                    startTime = state.startTime,
                    finishTime = state.finishTime
                )
                viewModelScope.launch {
                    repository.addTask(task)
                }
            }

            is TaskScreenEvent.EditTaskEvent -> viewModelScope.launch {
                repository.editTask(Task.emptyMock())
            }

            is TaskScreenEvent.onNameChanged -> {
                state = state.copy(name = event.text)
                Log.i("TaskVM", "${state}")
            }

            is TaskScreenEvent.onDescriptionChanged -> state = state.copy(description = event.text)
        }
    }

    private suspend fun getTaskById(taskId: Int): TaskScreenState {
        val task = repository.getTaskById(taskId) ?: Task.emptyMock()
        return TaskScreenState(
            name = task.name,
            description = task.description,
            startTime = task.startTime,
            finishTime = task.finishTime,
        )
    }
}

@AssistedFactory
interface TaskViewModelFactory {
    fun create(taskId: Int): TaskViewModel
}
