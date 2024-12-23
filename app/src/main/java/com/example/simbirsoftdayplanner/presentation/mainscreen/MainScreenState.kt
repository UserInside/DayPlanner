package com.example.simbirsoftdayplanner.presentation.mainscreen

import kotlinx.datetime.LocalTime


sealed class MainScreenEvent{
    object DeleteTaskEvent: MainScreenEvent()
    class OnTaskSelectedEvent(val taskId: Int): MainScreenEvent()
    class OnDateSelectedEvent(val date: Long): MainScreenEvent()
}

data class MainScreenState(
    val tasksList: List<TaskModel> = listOf(),
    val chosenTaskId: Int = 0,
    val selectedDate: Int = 0,
) {

}

data class TaskModel(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val startTime: LocalTime = LocalTime(0, 0),
    val finishTime: LocalTime = LocalTime(0, 0),
    val isSelected: Boolean = false,
){

}