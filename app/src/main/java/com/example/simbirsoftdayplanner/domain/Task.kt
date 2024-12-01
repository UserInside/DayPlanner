package com.example.simbirsoftdayplanner.domain

data class Task(
    val name: String,
    val targetTime: Double,
    val duration: Double,
    val description: String,
)
