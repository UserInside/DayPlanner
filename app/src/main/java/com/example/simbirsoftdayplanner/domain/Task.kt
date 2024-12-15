package com.example.simbirsoftdayplanner.domain

data class Task(
    val id: Int,
    val name: String,
    val description: String,
    val startTime: Long,
    val finishTime: Long,
) {
    companion object {

        fun mock() = Task(
            id = 17,
            name = "Mock Task",
            description = "Mock task description",
            startTime = 147600000,
            finishTime = 147610000,
        )
    }
}
