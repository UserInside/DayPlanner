package com.example.simbirsoftdayplanner.domain

data class Task(
    val id: Int,
    val dateStart: Int,
    val dateFinish: Int,
    val name: String,
    val description: String,
){
    companion object {

        fun mock() = Task(
            id = 1,
            dateStart = 147600000,
            dateFinish = 147610000,
            name = "Mock Task",
            description = "Mock task description",
        )
    }
}
