package com.ahsan.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ahsan.core.extension.toFormattedTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        isError = selectedDate.isEmpty(),
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