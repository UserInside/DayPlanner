package com.example.simbirsoftdayplanner.presentation.mainscreen

import com.example.simbirsoftdayplanner.domain.Task


sealed class MainScreenEvent{
//    class AddTaskEvent: MainScreenEvent()
//    class EditTaskEvent: MainScreenEvent()
    class DeleteTaskEvent: MainScreenEvent()
}

class MainScreenState(
    val tasksList: List<Task> = listOf()
) {

}