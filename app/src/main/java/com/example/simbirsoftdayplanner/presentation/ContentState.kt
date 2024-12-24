package com.example.simbirsoftdayplanner.presentation

sealed class ContentState {
    object Loading : ContentState()
    object Done : ContentState()
    object Idle : ContentState()
}