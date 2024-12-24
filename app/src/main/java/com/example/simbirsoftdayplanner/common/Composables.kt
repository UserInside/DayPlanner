package com.example.simbirsoftdayplanner.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.commandiron.compose_loading.InstaSpinner
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

@Composable
fun Loader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark),
        contentAlignment = Alignment.Center,
    ) {
        InstaSpinner(color = Colors.SemiLight)
    }
}
