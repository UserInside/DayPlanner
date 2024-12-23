package com.example.simbirsoftdayplanner.presentation.taskscreen

import android.util.Log
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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atDate

@HiltViewModel(assistedFactory = TaskViewModelFactory::class)
class TaskViewModel @AssistedInject constructor(
    private val taskInteractor: TaskInteractor,
    @Assisted("p1") val taskId: Int,
    @Assisted("p2") val selectedDate: Int,
) : ViewModel() {

    var state by mutableStateOf(TaskScreenState())

    init {
        Log.i("TDRI", "TVM state.startTime - ${state.startTime}")
        Log.i("TDRI", "TVM taskId - $taskId")
        Log.i("TDRI", "TVM selectedDate - $selectedDate")

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
                    startTime = state.startTime.atDate(LocalDate.fromEpochDays(selectedDate)), //написать номальную дату
                    finishTime = state.finishTime.atDate(LocalDate.fromEpochDays(selectedDate)), //написать номальную дату
                )
                viewModelScope.launch {
                    taskInteractor.addTask(task)
                }
            }

            is TaskScreenEvent.EditTaskEvent -> viewModelScope.launch {
                taskInteractor.editTask(Task())
            }

            is TaskScreenEvent.OnNameUpdatedEvent ->
                state = state.copy(name = event.text)

            is TaskScreenEvent.OnDescriptionUpdatedEvent -> state =
                state.copy(description = event.text)

            is TaskScreenEvent.OnStartTimeUpdatedEvent -> state =
                state.copy(startTime = LocalTime(event.hours, event.minutes))

            is TaskScreenEvent.OnFinishTimeUpdatedEvent -> state =
                state.copy(finishTime = LocalTime(event.hours, event.minutes))
        }
    }

    private suspend fun getTaskById(taskId: Int): TaskScreenState {
        val task = taskInteractor.getTaskById(taskId) ?: Task()
        return TaskScreenState(
            name = task.name,
            description = task.description,
            startTime = task.startTime.time, //исправить время. может убрать на часы
            finishTime = task.finishTime.time,
        )
    }
}

@AssistedFactory
interface TaskViewModelFactory {
    fun create(
        @Assisted("p1") taskId: Int,
        @Assisted("p2") selectedDate: Int
    ): TaskViewModel
}
