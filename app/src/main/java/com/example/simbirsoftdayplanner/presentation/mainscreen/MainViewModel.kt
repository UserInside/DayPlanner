package com.example.simbirsoftdayplanner.presentation.mainscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskInteractor: TaskInteractor,
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())

    init {
        viewModelScope.launch {
            val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

            state = state.copy(
                tasksList = getTasksListByDate(today),
                selectedDate = today.toEpochDays()
            )
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnDateSelectedEvent -> {
                val instant = Instant.fromEpochMilliseconds(event.date)
                val date: LocalDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
                viewModelScope.launch {
                    state = state.copy(
                        tasksList = getTasksListByDate(date),
                        selectedDate = date.toEpochDays()
                    )
                }
            }

            is MainScreenEvent.OnTaskLineSelectedEvent -> {
                val newTaskList = selectLine(event.startTime.hour)
                state = state.copy(tasksList = newTaskList, selectedTaskId = event.taskId)
            }

            is MainScreenEvent.OnEmptyLineSelectedEvent -> {
                val newTaskList = selectLine(event.selectedHour)
                state = state.copy(tasksList = newTaskList, selectedHour = event.selectedHour)
            }

            is MainScreenEvent.DeleteTaskEvent -> {
                viewModelScope.launch {
                    taskInteractor.deleteTask(state.selectedTaskId)
                    //getTAskListByDate ??
                }
            }
        }
    }

     suspend fun getTasksListByDate(date: LocalDate): List<TaskModel> {
        val taskList = taskInteractor.getTaskListByDate(date)
        val hoursList = taskList.map { it.startTime.hour }
//        Log.i("TLST", "hours list - $hoursList")

        val resultList = mutableListOf<TaskModel>()
        for (i in 0..23) {
            if (i !in hoursList) {
                resultList.add(TaskModel(startTime = LocalTime(i, 0)))
            } else {
                val task: Task = taskList.find { it.startTime.hour == i } ?: Task()

                val taskModel = TaskModel(
                    id = task.id,
                    name = task.name,
                    description = task.description,
                    startTime = task.startTime.time, //может поставить Int hour ? зачем мне полное время? upd. пока не надо
                    // надо где-то добавить стейт для выделения и видимо по нему ориентироваться

                    // 2. может сделать как-то ограничение чтобы минуты всегда 00 были?
                )
                resultList.add(taskModel)
            }
        }

        return resultList
    }


    private fun selectLine(selectedLineHour: Int): List<TaskModel>{
        val currentList = state.tasksList
        val newList = mutableListOf<TaskModel>()

        currentList.forEach { task ->
            if (selectedLineHour == task.startTime.hour) {
                task.isSelected = !task.isSelected
            } else {
                task.isSelected = false
            }
            newList.add(task)
        }
        return newList
    }
}