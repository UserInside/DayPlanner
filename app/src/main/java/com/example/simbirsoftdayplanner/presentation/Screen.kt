package com.example.simbirsoftdayplanner.presentation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object MainScreen: Screen()

    @Serializable
    class TaskScreen(
        val taskId: Int = 0,
        val selectedDate: Int = 0,
        val selectedHour: Int = 0
    ): Screen()
}