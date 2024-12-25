package com.example.simbirsoftdayplanner.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.simbirsoftdayplanner.presentation.mainscreen.MainScreen
import com.example.simbirsoftdayplanner.presentation.taskscreen.TaskScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen) {
        composable<Screen.MainScreen> {
            MainScreen(
                onNavigate = { taskId, selectedDate, selectedHour ->
                    navController.navigate(Screen.TaskScreen(taskId, selectedDate, selectedHour))
                },
            )
        }
        composable<Screen.TaskScreen> {
            val args = it.toRoute<Screen.TaskScreen>()
            TaskScreen(
                taskId = args.taskId,
                selectedDate = args.selectedDate,
                selectedHour = args.selectedHour,
                onNavigate = { navController.navigate(Screen.MainScreen) },

                )
        }
    }
}