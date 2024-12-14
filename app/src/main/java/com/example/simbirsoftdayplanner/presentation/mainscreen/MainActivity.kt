package com.example.simbirsoftdayplanner.presentation.mainscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.presentation.theme.Colors
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val vm = ViewModelProvider(this)[MainViewModel::class.java]


        val mockMap = mapOf(
            Pair(7, Task.mock()),
            Pair(11, Task.mock()),
            Pair(18, Task.mock()),
        )

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.MainBackground)
            ) {
                BestDatePickerEver()

                DayTable(mockMap)
            }

            val task = Task.mock()


//            BottomPanelScaffold(modifier = Modifier, content = {
////                ActionButton(vm.editTask(), "Изменить")
////                ActionButton(vm.deleteTask(), "Удалить")
//
//            })
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
                    text = "${task.dateStart} - ${task.dateFinish}",
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
    val isSelected = remember{mutableStateOf(false)}

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
fun BottomPanelScaffold(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    LazyRow(
        modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        content
    }

}

@Composable
private fun ActionButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = Modifier.background(Colors.ButtonBackground),
        onClick = { onClick },
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
