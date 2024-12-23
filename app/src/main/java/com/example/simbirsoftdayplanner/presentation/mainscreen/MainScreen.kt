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
import com.example.simbirsoftdayplanner.presentation.theme.Colors

@Composable
fun MainScreen(
    onNavigate: (Int, Int) -> Unit,
) {
    val viewModel = hiltViewModel<MainViewModel>()
    MainView(
        toTaskScreen = onNavigate, state = viewModel.state, onEvent = viewModel::onEvent
    )
}

@Composable
fun MainView(
    toTaskScreen: (Int, Int) -> Unit,
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit = {},
) {

    var bottomBarState by remember { mutableStateOf(BottomBarState.NoLineSelectedState) }

    Scaffold(
        containerColor = Colors.Dark,
        floatingActionButton = {
            if (bottomBarState == BottomBarState.NoLineSelectedState) {
                FloatingActionButton(
                    containerColor = Colors.Accent,
                    contentColor = Colors.Dark,
                    content = { Icon(Icons.Filled.Add, contentDescription = "Add") },
                    onClick = {
                        toTaskScreen(state.chosenTaskId, state.selectedDate)
                    },
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            when (bottomBarState) {
                BottomBarState.TaskLineSelectedState -> TaskLineSelectedBottomBar(onClick = { MainScreenEvent.DeleteTaskEvent },
                    onNavigate = { toTaskScreen(state.chosenTaskId, state.selectedDate) })

                BottomBarState.EmptyLineSelectedState -> EmptyLineSelectedBottomBar(onNavigate = {
                    toTaskScreen(state.chosenTaskId, state.selectedDate)
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
                onDateSelected = { onEvent(MainScreenEvent.OnDateSelectedEvent(it)) }
            )

            DayTable(state.tasksList, onLineClick = { state ->
                bottomBarState =
                    if (bottomBarState == state) BottomBarState.NoLineSelectedState else state
            })
        }
    }
}

@Composable
private fun DayTable(taskList: List<TaskModel>, onLineClick: (BottomBarState) -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .background(Colors.Dark)
            .padding(8.dp)
    ) { //todo test из бд достаем несколько задач и после из вьюмоделе должны получить список из 24 эл с правильными задачами.
        taskList.forEach {
            if (it.isSelected) {
                TaskTableLine(it) {
                    onLineClick(BottomBarState.TaskLineSelectedState)
                }
            } else {
                EmptyTableLine(it) {
                    onLineClick(BottomBarState.EmptyLineSelectedState)
                }
            }
        }
    }
}

@Composable
private fun TaskTableLine(task: TaskModel, onEvent: (MainScreenEvent) -> Unit) {

    var isTaskSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .clip(Shapes().extraSmall)
            .clickable {
                isTaskSelected = !isTaskSelected
                onEvent(MainScreenEvent.OnTaskSelectedEvent(task.id))
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
                    text = "${task.startTime} - ${task.finishTime}",
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
private fun EmptyTableLine(task: TaskModel, onClick: (MainScreenEvent) -> Unit) {
    var isEmptyLineSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .clip(Shapes().extraSmall)
            .fillMaxWidth()
            .background(if (task.isSelected) Colors.SemiLight else Colors.SemiDark)
            .clickable {
                isEmptyLineSelected = !isEmptyLineSelected
                onClick(MainScreenEvent.OnTaskSelectedEvent(task.id))
            },                                                                             // add click action,
        contentAlignment = Alignment.CenterEnd,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
            text = "${task.startTime}",
            fontSize = 8.sp,
            color = if (isEmptyLineSelected) Colors.Dark else Colors.SemiLight,
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
    } //todo snackbar deleted
}

@Composable
private fun TaskLineSelectedBottomBar(
    onNavigate: () -> Unit, onClick: (MainScreenEvent) -> Unit //todo удалить?
) {
    BottomAppBar(
        modifier = Modifier.padding(vertical = 0.dp), //todo   удалить
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
                onClick = { MainScreenEvent.DeleteTaskEvent },
                text = "Delete"
            )
            ActionButton(
                modifier = Modifier.weight(1f), onClick = onNavigate, text = "Edit"
            )
        }
    } //todo snackbar deleted
}


@Composable
fun ActionButton(
    modifier: Modifier,
    onClick: () -> Unit, text: String
) {
    Button(
        modifier = modifier,
        onClick = { onClick() }, colors = ButtonColors(
            containerColor = Colors.Accent,
            contentColor = Colors.Dark,
            disabledContentColor = Colors.Dark,
            disabledContainerColor = Colors.Dark
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
        Log.i("MVMDate", "${it}")  //todo почему вызывается два раза?

    }


}

enum class BottomBarState {
    TaskLineSelectedState, EmptyLineSelectedState, NoLineSelectedState
}