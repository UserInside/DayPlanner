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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.presentation.theme.Colors

@Composable
fun MainScreen(
    onNavigate: (Screen) -> Unit,
) {
    val viewModel = hiltViewModel<MainViewModel>()
    MainView(
        onNavigate = onNavigate, state = viewModel.state, onEvent = viewModel::onEvent
    )
}

@Composable
fun MainView(
    onNavigate: (Screen) -> Unit,
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit = {},
) {

    var bottomBarState by remember { mutableStateOf(BottomBarState.NoLineSelectedState) }

//    val mockMap = mapOf(
//        Pair(3, Task.mock()),
//        Pair(8, Task.mock()),
//        Pair(11, Task.mock()),
//    )

    Scaffold(containerColor = Colors.MainBackground, floatingActionButton = {
        if (bottomBarState == BottomBarState.NoLineSelectedState) {
            FloatingActionButton(
                containerColor = Colors.chosenDate,
                contentColor = Colors.MainBackground,
                content = { Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                onClick = {
                    onNavigate(Screen.TaskScreen(2))
                },
            )
        }
    }, floatingActionButtonPosition = FabPosition.End, bottomBar = {
        when (bottomBarState) {
            BottomBarState.TaskLineSelectedState -> TaskLineSelectedBottomBar(onClick = { MainScreenEvent.DeleteTaskEvent },
                onNavigate = { onNavigate(Screen.TaskScreen(2)) })

            BottomBarState.EmptyLineSelectedState -> EmptyLineSelectedBottomBar(onNavigate = {
                onNavigate(
                    Screen.TaskScreen(2)
                )
            })

            BottomBarState.NoLineSelectedState -> null
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            BestDatePickerEver(
                onDateSelected = { onEvent(MainScreenEvent.onDateSelectedEvent(it)) }
            )

            DayTable(state.tasksList, onLineClick = { state ->
                bottomBarState =
                    if (bottomBarState == state) BottomBarState.NoLineSelectedState else state
            })
        }
    }
}

@Composable
private fun DayTable(taskList: List<Task>, onLineClick: (BottomBarState) -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .background(Colors.MainBackground)
            .padding(8.dp)
    ) {
        val hoursList = taskList.map { it.id }
        for (i in 0..23) {
            if (i !in hoursList) {
                EmptyTableLine(i = i) {
                    onLineClick(BottomBarState.EmptyLineSelectedState)
                }
            } else {
                TaskTableLine(task = taskList.find { it.id == i } ?: Task.mock()) {
                    onLineClick(BottomBarState.TaskLineSelectedState)
                }
            }
        }
    }
}

@Composable
private fun TaskTableLine(task: Task, onEvent: (MainScreenEvent) -> Unit) {

    var isTaskSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .clip(Shapes().extraSmall)
            .clickable {
                isTaskSelected = !isTaskSelected
                onEvent(MainScreenEvent.TaskClickedEvent(task.id))
            }
            .fillMaxWidth()
            .background(
                if (isTaskSelected) Colors.Time else Colors.ButtonBackground
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
                    color = if (isTaskSelected) Colors.MainBackground else Colors.Text,

                    )
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = "${task.startTime} - ${task.finishTime}",
                    fontSize = 12.sp,
                    color = if (isTaskSelected) Colors.MainBackground else Colors.Time,
                )
            }
            Text(
                modifier = Modifier.padding(2.dp),
                text = task.description,
                fontSize = 8.sp,
                color = if (isTaskSelected) Colors.MainBackground else Colors.Text,

                )
        }
    }
}

@Composable
private fun EmptyTableLine(i: Int, onClick: () -> Unit) {
    var isEmptyLineSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .clip(Shapes().extraSmall)
            .fillMaxWidth()
            .background(if (isEmptyLineSelected) Colors.Time else Colors.ButtonBackground)
            .clickable {
                isEmptyLineSelected = !isEmptyLineSelected
                onClick()
            }, // add click action,
        contentAlignment = Alignment.CenterEnd,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
            text = "$i:00",
            fontSize = 8.sp,
            color = if (isEmptyLineSelected) Colors.MainBackground else Colors.Time,
        )
    }
}

@Composable
private fun EmptyLineSelectedBottomBar(
    onNavigate: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier.padding(vertical = 0.dp),
        containerColor = Colors.MainBackground,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center,
        ) {
            ActionButton(onClick = onNavigate, text = "+ Add")
        }
    } //todo snackbar deleted
}

@Composable
private fun TaskLineSelectedBottomBar(
    onNavigate: () -> Unit, onClick: (MainScreenEvent) -> Unit //todo удалить?
) {
    BottomAppBar(
        modifier = Modifier.padding(vertical = 0.dp),
        containerColor = Colors.MainBackground,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ActionButton(onClick = { MainScreenEvent.DeleteTaskEvent }, text = "Delete")
            ActionButton(
                onClick = onNavigate, text = "Edit"
            )  // navigate лушче писать тут или передавать из вне?
        }
    } //todo snackbar deleted
}


@Composable
fun ActionButton(
    onClick: () -> Unit, text: String
) {
    Button(
        modifier = Modifier.width(150.dp), //todo поменять на размер бОльшей кнопки
        onClick = { onClick() }, colors = ButtonColors(
            containerColor = Colors.chosenDate,
            contentColor = Colors.MainBackground,
            disabledContentColor = Colors.MainBackground,
            disabledContainerColor = Colors.MainBackground
        )
    ) {
        Text(text, fontSize = 12.sp)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BestDatePickerEver(onDateSelected: (Long) -> Unit) {

    val datePickerState = rememberDatePickerState()

    DatePicker(
        state = datePickerState,
        title = {},
        headline = {},
        showModeToggle = false,
        colors = DatePickerDefaults.colors(
            containerColor = Colors.MainBackground,
            weekdayContentColor = Colors.Text,
            navigationContentColor = Colors.Text,
            yearContentColor = Colors.Text,
            currentYearContentColor = Colors.Text,
            selectedYearContentColor = Colors.Text,
            selectedYearContainerColor = Colors.chosenDate,
            dayContentColor = Colors.Text,
            selectedDayContentColor = Colors.MainBackground,
            selectedDayContainerColor = Colors.chosenDate,
            todayContentColor = Colors.Text,
            todayDateBorderColor = Colors.chosenDate,
            dividerColor = Colors.ButtonBackground,
        ),
    )

    datePickerState.selectedDateMillis?.let {
        onDateSelected(it)
        Log.i("MVMDate", "${it}")  //todo почему вызывается два раза?

    }


}

enum class BottomBarState {
    TaskLineSelectedState, EmptyLineSelectedState, NoLineSelectedState
}