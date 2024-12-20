package com.example.simbirsoftdayplanner.presentation.mainscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.presentation.taskscreen.TaskScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.MainScreen) {
                composable<Screen.MainScreen> {
                    MainScreen(
                        onNavigate = { navController.navigate(Screen.TaskScreen(2)) },
                    )
                }
                composable<Screen.TaskScreen> {
                    val args = it.toRoute<Screen.TaskScreen>()
                    TaskScreen(
                        taskId = args.taskId,
                        onNavigate = { navController.navigate(Screen.MainScreen) },

                    )
                }
            }
        }
    }

}


sealed class Screen {
    @Serializable
    object MainScreen: Screen()

    @Serializable
    data class TaskScreen(val taskId: Int): Screen()
}