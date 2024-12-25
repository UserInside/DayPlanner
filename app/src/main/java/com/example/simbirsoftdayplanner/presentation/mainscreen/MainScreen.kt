package com.example.simbirsoftdayplanner.presentation.mainscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simbirsoftdayplanner.common.ActionButton
import com.example.simbirsoftdayplanner.common.Loader
import com.example.simbirsoftdayplanner.presentation.ContentState
import com.example.simbirsoftdayplanner.presentation.theme.Colors

@Composable
fun MainScreen(
    onNavigate: (Int, Int, Int) -> Unit,
) {
    val viewModel = hiltViewModel<MainViewModel>()
    MainView(
        toTaskScreen = onNavigate, state = viewModel.state, onEvent = viewModel::onEvent
    )
}

@Composable
fun MainView(
    toTaskScreen: (Int, Int, Int) -> Unit,
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit = {},
) {
    Scaffold(
        containerColor = Colors.Dark,
        floatingActionButton = {
            if (state.bottomBarState == BottomBarState.NoLineSelectedState) {
                FloatingActionButton(
                    containerColor = Colors.Accent,
                    contentColor = Colors.Dark,
                    content = { Icon(Icons.Filled.Add, contentDescription = "Add") },
                    onClick = {
                        toTaskScreen(state.selectedTaskId, state.selectedDate, state.selectedHour)
                    },
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            when (state.bottomBarState) {
                BottomBarState.TaskLineSelectedState -> TaskLineSelectedBottomBar(
                    onClick = { onEvent(MainScreenEvent.DeleteTaskEvent) },
                    onNavigate = {
                        toTaskScreen(state.selectedTaskId, state.selectedDate, state.selectedHour)
                    })
                BottomBarState.EmptyLineSelectedState -> EmptyLineSelectedBottomBar(
                    onNavigate = {
                        toTaskScreen(state.selectedTaskId, state.selectedDate, state.selectedHour)
                    })
                BottomBarState.NoLineSelectedState -> null
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Log.i("MVM", "1 -- ${state.selectedDate}")

            DatePicker(
                onDateSelected = { onEvent(MainScreenEvent.OnDateSelectedEvent(it)) }
            )

            if (state.contentState == ContentState.Loading) { Loader() }
            if (state.contentState == ContentState.Done) {
                Log.i("MVM", "2--${state.selectedDate}")
                TasksColumn(
                    taskList = state.tasksList,
                    onEvent = onEvent,
                    onLineClick = {})
            }
        }
    }
}

@Composable
private fun TasksColumn(
    taskList: List<TaskModel>,
    onEvent: (MainScreenEvent) -> Unit = {},
    onLineClick: (BottomBarState) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .background(Colors.Dark)
            .padding(8.dp)
    ) {
        taskList.forEach { task ->
            if (task.id != 0) {
                TaskTableLine(
                    task = task,
                    onClick = {
                        onEvent(MainScreenEvent.OnTaskLineSelectedEvent(task.id, task.startTime))
                        onLineClick(BottomBarState.TaskLineSelectedState)
                    })
            } else {
                EmptyTableLine(task) {
                    onEvent(MainScreenEvent.OnEmptyLineSelectedEvent(task.startTime.hour))
                    onLineClick(BottomBarState.EmptyLineSelectedState)
                }
            }
        }
    }
}

@Composable
private fun TaskTableLine(
    task: TaskModel,
    onClick: () -> Unit
) {
    val isTaskSelected = task.isSelected

    Box(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .clip(Shapes().extraSmall)
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .background(
                if (isTaskSelected) Colors.SemiLight else Colors.SemiDark
            ),
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = task.name,
                    fontSize = 12.sp,
                    color = if (isTaskSelected) Colors.Dark else Colors.Light,

                    )
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = "${task.startTime}",
                    fontSize = 12.sp,
                    color = if (isTaskSelected) Colors.Dark else Colors.SemiLight,
                )
            }
            Text(
                modifier = Modifier.padding(2.dp),
                text = task.description,
                fontSize = 8.sp,
                color = if (isTaskSelected) Colors.Dark else Colors.Light,
                )
        }
    }
}

@Composable
private fun EmptyTableLine(task: TaskModel, onClick: () -> Unit) {

    val isLineSelected = task.isSelected

    Box(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .clip(Shapes().extraSmall)
            .fillMaxWidth()
            .background(if (isLineSelected) Colors.SemiLight else Colors.SemiDark)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.CenterEnd,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
            text = "${task.startTime}",
            fontSize = 8.sp,
            color = if (isLineSelected) Colors.Dark else Colors.SemiLight,
        )
    }
}

@Composable
private fun EmptyLineSelectedBottomBar(
    onNavigate: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier.padding(vertical = 0.dp),
        containerColor = Colors.Dark,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center,
        ) {
            ActionButton(modifier = Modifier.weight(1f), onClick = onNavigate, text = "+ Add")
        }
    }
}

@Composable
private fun TaskLineSelectedBottomBar(
    onNavigate: () -> Unit, onClick: () -> Unit
) {
    BottomAppBar(
        containerColor = Colors.Dark,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ActionButton(
                modifier = Modifier.weight(1f),
                onClick = onClick,
                text = "Delete"
            )
            ActionButton(
                modifier = Modifier.weight(1f), onClick = onNavigate, text = "Edit"
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePicker(onDateSelected: (Long) -> Unit) {

    val datePickerState = rememberDatePickerState()

    DatePicker(
        state = datePickerState,
        title = null,
        headline = null,
        showModeToggle = false,
        colors = DatePickerDefaults.colors(
            containerColor = Colors.Dark,
            weekdayContentColor = Colors.Light,
            navigationContentColor = Colors.Light,
            yearContentColor = Colors.Light,
            currentYearContentColor = Colors.Light,
            selectedYearContentColor = Colors.Light,
            selectedYearContainerColor = Colors.Accent,
            dayContentColor = Colors.Light,
            selectedDayContentColor = Colors.Dark,
            selectedDayContainerColor = Colors.Accent,
            todayContentColor = Colors.Light,
            todayDateBorderColor = Colors.Accent,
            dividerColor = Colors.SemiDark,
        ),
    )

    datePickerState.selectedDateMillis?.let {
        onDateSelected(it)
    }
}
