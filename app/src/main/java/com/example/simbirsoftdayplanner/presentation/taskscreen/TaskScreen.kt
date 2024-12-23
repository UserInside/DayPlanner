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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simbirsoftdayplanner.presentation.mainscreen.ActionButton
import com.example.simbirsoftdayplanner.presentation.mainscreen.Screen
import com.example.simbirsoftdayplanner.presentation.theme.Colors
import kotlinx.datetime.LocalTime
import java.util.Calendar

@Composable
fun TaskScreen(
    taskId: Int,
    selectedDate: Int,
    onNavigate: (Screen) -> Unit,
) {
    val viewModel: TaskViewModel = hiltViewModel<TaskViewModel, TaskViewModelFactory>(
        creationCallback = { factory -> factory.create(taskId, selectedDate) }
    )
    TaskView(
        onNavigate = onNavigate,
        state = viewModel.state,
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
        containerColor = Colors.Dark,
        bottomBar = {
            BottomAppBar(
                containerColor = Colors.Dark,
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(Screen.MainScreen) },
                        text = "Cancel"
                    )
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onEvent(TaskScreenEvent.AddTaskEvent)
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
                onTextChanged = { onEvent(TaskScreenEvent.OnNameUpdatedEvent(it)) })

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                name = "Description",
                state.description,
                onTextChanged = { onEvent(TaskScreenEvent.OnDescriptionUpdatedEvent(it)) })

            Spacer(modifier = Modifier.height(12.dp))

            TimeInputRow(
                name = "Start Time",
                time = state.startTime,
                onTimeUpdated = { onEvent(TaskScreenEvent.OnStartTimeUpdatedEvent(it.hour, it.minute)) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            TimeInputRow(
                name = "Finish Time",
                time = state.finishTime,
                onTimeUpdated = { onEvent(TaskScreenEvent.OnFinishTimeUpdatedEvent(it.hour, it.minute)) }
            )
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
            cursorColor = Colors.SemiLight,
            focusedLabelColor = Colors.Accent,
            unfocusedLabelColor = Colors.Accent,
            focusedTextColor = Colors.Light,
            unfocusedTextColor = Colors.Light,
            focusedBorderColor = Colors.SemiLight,
            unfocusedBorderColor = Colors.SemiLight
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimeInputRow(name: String, time: LocalTime, onTimeUpdated: (TimePickerState) -> Unit) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = time.hour ?: currentTime.get(Calendar.HOUR_OF_DAY), //todo убрать?
        initialMinute = time.minute ?: currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    timePickerState.let {
        onTimeUpdated(it)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = name,
            fontSize = 24.sp,
            color = Colors.Accent,
        )
        TimeInput(
            state = timePickerState,
            colors = TimePickerDefaults.colors(
                timeSelectorSelectedContainerColor = Colors.SemiLight,
                timeSelectorUnselectedContainerColor = Colors.SemiDark,
                timeSelectorSelectedContentColor = Colors.Accent,
                timeSelectorUnselectedContentColor = Colors.SemiLight,
            )
        )
    }
}