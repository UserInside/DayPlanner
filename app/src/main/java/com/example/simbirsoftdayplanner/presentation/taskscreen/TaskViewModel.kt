package com.example.simbirsoftdayplanner.presentation.taskscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = TaskViewModelFactory::class)
class TaskViewModel @AssistedInject constructor(
    private val repository: TaskRepository,
    @Assisted taskId: String,
) : ViewModel() {

    var state = mutableStateOf(TaskScreenState(Task.mock()))
        private set

    init {
        viewModelScope.launch {
            state.value.task = getTaskById(taskId.toInt())
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

@AssistedFactory
interface TaskViewModelFactory {
    fun create(taskId: String): TaskViewModel
}
