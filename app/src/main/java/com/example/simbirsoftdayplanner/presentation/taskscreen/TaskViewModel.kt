package com.example.simbirsoftdayplanner.presentation.taskscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = TaskViewModelFactory::class)
class TaskViewModel @AssistedInject constructor(
    private val taskInteractor: TaskInteractor,
    @Assisted val taskId: Int,
) : ViewModel() {

    var state by mutableStateOf(TaskScreenState())

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
                    taskInteractor.addTask(task)
                }
            }

            is TaskScreenEvent.EditTaskEvent -> viewModelScope.launch {
                taskInteractor.editTask(Task.emptyMock())
            }

            is TaskScreenEvent.onNameChangedEvent -> {
                state = state.copy(name = event.text)
            }

            is TaskScreenEvent.onDescriptionChangedEvent -> state =
                state.copy(description = event.text)
        }
    }

    private suspend fun getTaskById(taskId: Int): TaskScreenState {
        val task = taskInteractor.getTaskById(taskId) ?: Task.emptyMock()
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
