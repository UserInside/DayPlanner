package com.example.simbirsoftdayplanner.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object MainScreen: Screen

    @Serializable
    class TaskScreen(
        val taskId: Int = 0,
        val selectedDate: Int = 0,
        val selectedHour: Int = 0
    ): Screen
}