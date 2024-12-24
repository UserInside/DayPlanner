package com.example.simbirsoftdayplanner.presentation.mainscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.simbirsoftdayplanner.presentation.Screen
import com.example.simbirsoftdayplanner.presentation.taskscreen.TaskScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.MainScreen) {
                composable<Screen.MainScreen> {
                    MainScreen(
                        onNavigate = { taskId, selectedDate, selectedHour ->
                            navController.navigate(Screen.TaskScreen(taskId, selectedDate, selectedHour)) },
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
    }
}
