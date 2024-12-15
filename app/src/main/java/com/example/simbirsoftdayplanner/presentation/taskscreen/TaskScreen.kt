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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simbirsoftdayplanner.presentation.mainscreen.ActionButton
import com.example.simbirsoftdayplanner.presentation.theme.Colors
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
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
                    ActionButton(onClick = { onCancelClick() }, text = "Cancel")
                    ActionButton(onClick = { onSaveClick() }, text = "Save")
                }
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            OutlinedTextField(name = "Name")

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(name = "Description")

            Spacer(modifier = Modifier.height(12.dp))

            val x = timeInputRow(name = "Start TIme")

            Spacer(modifier = Modifier.height(12.dp))

            val y = timeInputRow(name = "Finish TIme")

        }
    }


}

@Composable
private fun OutlinedTextField(name: String) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        textStyle = TextStyle(
            fontSize = 24.sp
        ),
        onValueChange = { text = it },
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
private fun timeInputRow(name: String): TimePickerState {
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
            fontSize = 12.sp,
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