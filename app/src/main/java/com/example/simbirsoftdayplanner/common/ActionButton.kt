package com.example.simbirsoftdayplanner.common

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.simbirsoftdayplanner.presentation.theme.Colors

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
