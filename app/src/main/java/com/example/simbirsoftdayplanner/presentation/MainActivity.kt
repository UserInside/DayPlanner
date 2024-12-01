package com.example.simbirsoftdayplanner.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.simbirsoftdayplanner.domain.Day
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.presentation.theme.Colors
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            Calendar(){
//
//            }
//            TaskList(day){
        }
    }
}

@Composable
private fun TaskList(day: Day){
    LazyColumn() {
        day.tasks
    }
}

@Composable
private fun Task(task: Task) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Colors.ButtonBackground)) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = task.name,
                    color = Colors.Text,
                )
                Text(text = task.targetTime.toString(),
                    color = Colors.Time,
                )
            }
            Text(
                text = task.description,
                color = Colors.Text,
            )
        }
    }
}



