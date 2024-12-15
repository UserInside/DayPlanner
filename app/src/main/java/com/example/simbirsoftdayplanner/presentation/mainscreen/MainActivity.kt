package com.example.simbirsoftdayplanner.presentation.mainscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simbirsoftdayplanner.presentation.taskscreen.TaskScreen
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = MainScreen) {
                composable<MainScreen> {
                    MainScreen(onAddClick = {navController.navigate(TaskScreen)}
                    )
                }
                composable<TaskScreen> {
                    TaskScreen(
                        onSaveClick = { navController.navigate(MainScreen) },
                        onCancelClick = { navController.navigate(MainScreen) }
                    )
                }
            }
        }
    }
}

@Serializable
object MainScreen

@Serializable
object TaskScreen