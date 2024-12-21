package com.example.simbirsoftdayplanner.presentation.taskscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.presentation.mainscreen.ActionButton
import com.example.simbirsoftdayplanner.presentation.mainscreen.Screen
import com.example.simbirsoftdayplanner.presentation.theme.Colors
import java.sql.Timestamp
import java.util.Calendar

@Composable
fun TaskScreen(
    taskId: Int,
    onNavigate: (Screen) -> Unit,
) {
    val viewModel: TaskViewModel = hiltViewModel<TaskViewModel, TaskViewModelFactory>(
        creationCallback = { factory -> factory.create(taskId) }
    )
    TaskView(
        state = viewModel.state,
        onNavigate = onNavigate,
        onEvent = viewModel::onEvent
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskView(
    onNavigate: (Screen) -> Unit,
    state: TaskScreenState,
    onEvent: (TaskScreenEvent) -> Unit = {},

    ) {

    Scaffold(
        containerColor = Colors.MainBackground,

        bottomBar = {
            BottomAppBar(
                containerColor = Colors.MainBackground,
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ActionButton(onClick = { onNavigate(Screen.MainScreen) }, text = "Cancel")
                    ActionButton(
                        onClick = {
                            onEvent(TaskScreenEvent.AddTaskEvent())
                            onNavigate(Screen.MainScreen)
                        }, text = "Save"
                    )
                }
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            OutlinedTextField(
                name = "Name",
                state.name,
                onTextChanged = { onEvent(TaskScreenEvent.onNameChanged(it)) })

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                name = "Description",
                state.description,
                onTextChanged = { onEvent(TaskScreenEvent.onDescriptionChanged(it)) })

            Spacer(modifier = Modifier.height(12.dp))

            val x = timeInputRow(name = "Start Time", Timestamp(147610000))

            Spacer(modifier = Modifier.height(12.dp))

            val y = timeInputRow(name = "Finish Time", time = Timestamp(147600000))

        }
    }


}

@Composable
private fun OutlinedTextField(name: String, text: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        textStyle = TextStyle(
            fontSize = 24.sp
        ),
        onValueChange = { onTextChanged(it) },
        label = { Text(text = name) },
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Colors.Time,
            focusedLabelColor = Colors.chosenDate,
            unfocusedLabelColor = Colors.chosenDate,
            focusedTextColor = Colors.Text,
            unfocusedTextColor = Colors.Text,
            focusedBorderColor = Colors.Time,
            unfocusedBorderColor = Colors.Time
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun timeInputRow(name: String, time: Timestamp): TimePickerState {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = name,
            fontSize = 24.sp,
            color = Colors.chosenDate,
        )
        TimeInput(
            state = timePickerState,
            colors = TimePickerDefaults.colors(
                timeSelectorSelectedContainerColor = Colors.Time,
                timeSelectorUnselectedContainerColor = Colors.ButtonBackground,
                timeSelectorSelectedContentColor = Colors.chosenDate,
                timeSelectorUnselectedContentColor = Colors.Time,
            )
        )
    }
    return timePickerState
}