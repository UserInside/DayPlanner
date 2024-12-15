package com.example.simbirsoftdayplanner.presentation.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.presentation.theme.Colors
import java.util.Locale

@Composable
fun MainScreen(
    onAddClick: () -> Unit,
) {

    val mockMap = mapOf(
        Pair(7, Task.mock()),
        Pair(11, Task.mock()),
        Pair(18, Task.mock()),
    )

    Scaffold(
        containerColor = Colors.MainBackground,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Colors.chosenDate,
                contentColor = Colors.MainBackground,
                content = { Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                onClick = { onAddClick() },
            )
        },
        floatingActionButtonPosition = FabPosition.EndOverlay


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            BestDatePickerEver()
            DayTable(mockMap)
        }
    }
}


@Composable
private fun DayTable(taskMap: Map<Int, Task>) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .background(Colors.MainBackground)
            .padding(8.dp)
    ) {
        for (i in 0..23) {
            if (i !in taskMap.keys) {
                EmptyTableLine(i = i)
            } else {
                TaskTableLine(taskMap[i] ?: Task.mock())
            }
        }
    }
}

@Composable
private fun TaskTableLine(task: Task) {
    Box(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .clip(Shapes().extraSmall)
            .fillMaxWidth()
            .background(Colors.ButtonBackground)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = task.name,
                    fontSize = 12.sp,
                    color = Colors.Text,

                    )
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = "${task.startTime} - ${task.finishTime}",
                    fontSize = 12.sp,
                    color = Colors.Time,
                )
            }
            Text(
                modifier = Modifier.padding(2.dp),
                text = task.description,
                fontSize = 8.sp,
                color = Colors.Text,

                )
        }
    }
}

@Composable
private fun EmptyTableLine(i: Int) {
    val isSelected = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .clip(Shapes().extraSmall)
            .fillMaxWidth()
            .background(Colors.ButtonBackground)
            .clickable {
                isSelected.value != isSelected.value
            }, // add click action,
        contentAlignment = Alignment.CenterEnd,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
            text = "$i:00",
            fontSize = 8.sp,
            color = if (isSelected.value) Colors.chosenDate else Colors.Time,
        )

    }
}

@Composable
fun ActionButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = Modifier,
        onClick = { onClick() },
        colors = ButtonColors(
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
private fun BestDatePickerEver() {
    DatePicker(
        state = DatePickerState(locale = Locale.getDefault()),
        title = null,
        headline = null,
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
        )
    )

}