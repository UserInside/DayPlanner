package com.example.simbirsoftdayplanner.presentation.mainscreen

import com.example.simbirsoftdayplanner.domain.Task
import java.util.Date


sealed class MainScreenEvent{
    object DeleteTaskEvent: MainScreenEvent()
    class TaskClickedEvent(val taskId: Int): MainScreenEvent()
    class onDateSelectedEvent(val date: Long): MainScreenEvent()
}

data class MainScreenState(
    val tasksList: List<Task> = listOf(),
//    val chosenDate: Long = 0,
    val chosenTaskId: Int = 0,
) {

}