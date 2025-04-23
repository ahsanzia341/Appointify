package com.ahsan.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ahsan.composable.theme.SmartAppointmentTheme

@Composable
fun CalendarView() {
    Box {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CalendarPreview(){
    SmartAppointmentTheme {
        val state = rememberDatePickerState()
        DatePicker(state)
    }
}