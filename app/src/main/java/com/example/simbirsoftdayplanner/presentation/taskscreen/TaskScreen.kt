package com.example.simbirsoftdayplanner.presentation.taskscreen

import android.annotation.SuppressLint
import android.util.Log
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
import com.commandiron.compose_loading.InstaSpinner
import com.example.simbirsoftdayplanner.presentation.Screen
import com.example.simbirsoftdayplanner.common.ActionButton
import com.example.simbirsoftdayplanner.common.Loader
import com.example.simbirsoftdayplanner.presentation.ContentState
import com.example.simbirsoftdayplanner.presentation.theme.Colors
import kotlinx.datetime.LocalTime
import java.util.Calendar

@Composable
fun TaskScreen(
    taskId: Int,
    selectedDate: Int,
    selectedHour: Int,
    onNavigate: (Screen) -> Unit,
) {
    val viewModel: TaskViewModel = hiltViewModel<TaskViewModel, TaskViewModelFactory>(
        creationCallback = { factory -> factory.create(taskId, selectedDate, selectedHour) }
    )
    Log.i("TSCR", "hour in 0 - $${viewModel.state.startTime.hour}")

    TaskView(
        onNavigate = onNavigate,
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
    Log.i("TSCR", "hour in 1 - $${viewModel.state.startTime.hour}")

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskView(
    onNavigate: (Screen) -> Unit,
    state: TaskScreenState,
    onEvent: (TaskScreenEvent) -> Unit = {},

    ) {
    Log.i("TSCR", "hour in 2 - $${state.startTime.hour}")

    if (state.contentState == ContentState.Loading) { Loader() }

    if (state.contentState == ContentState.Done){
        Scaffold(
            containerColor = Colors.Dark,
            bottomBar = {
                BottomAppBar(
                    containerColor = Colors.Dark,
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
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

//            Log.i("TSCR", "hour before inputRow - ${state.startTime.hour}")
                Log.i("TSCR", "hour in 3 - ${state.startTime.hour}")

                TimeInputRow(
                    hour = state.startTime.hour,
                    onTimeUpdated = {
                        onEvent(
                            TaskScreenEvent.OnStartTimeUpdatedEvent(
                                it.hour,
                                it.minute
                            )
                        )
                    }
                )
            }
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
private fun TimeInputRow(hour: Int, onTimeUpdated: (TimePickerState) -> Unit) {

    Log.i("TSCR", "hour in TimeInputRow - $hour")

    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = 0,
        is24Hour = true,
    )

//    onTimeUpdated(timePickerState)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Start time",
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