package com.ahsan.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ahsan.core.extension.toFormattedTime
import java.util.Date

@Composable
fun ThemeDatePicker(label: String, onSelected: (Date) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember {
        mutableStateOf("")
    }
    ThemeTextField(
        label = label,
        value = selectedDate,
        isReadOnly = true,
        onClick = {
            showDatePicker = true
        },
        trailingIcon = R.drawable.ic_calendar
    ) {}
    if (showDatePicker) {
        DateTimePicker {
            if (it != null) {
                selectedDate = Date(it).toFormattedTime()
                onSelected(Date(it))
            }
            showDatePicker = false
        }
    }
}