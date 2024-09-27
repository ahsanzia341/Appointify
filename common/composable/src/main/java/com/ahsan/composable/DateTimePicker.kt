package com.ahsan.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(onDismiss: (Long?) -> Unit) {
    val calendar by remember {
        mutableStateOf(Calendar.getInstance())
    }
    var dateSelection by remember {
        mutableStateOf(DateSelection.DATE_PICKER)
    }
    val defaultCalendar = Calendar.getInstance()
    defaultCalendar.set(Calendar.HOUR_OF_DAY, 0)
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= defaultCalendar.time.time - 1000
        }

        override fun isSelectableYear(year: Int): Boolean {
            return year >= calendar.get(Calendar.YEAR)
        }
    })

    val timePickerState =
        rememberTimePickerState(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
    val selectedDateMillis = datePickerState.selectedDateMillis
    DatePickerDialog(
        onDismissRequest = {
            onDismiss(null)
        },
        confirmButton = {
            Row {
                TextButton(onClick = {
                    onDismiss(null)
                    calendar.time = Date(selectedDateMillis ?: 0L)
                    calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    calendar.set(Calendar.MINUTE, timePickerState.minute)
                    onDismiss(calendar.time.time)
                }) {
                    ThemeText(text = stringResource(id = R.string.confirm))
                }
                TextButton(onClick = {
                    dateSelection =
                        if (dateSelection == DateSelection.DATE_PICKER) DateSelection.TIME_PICKER else DateSelection.DATE_PICKER
                }) {

                    ThemeText(text = stringResource(id = if (dateSelection == DateSelection.DATE_PICKER) R.string.next else R.string.previous))
                }
            }
        }
    ) {
        if (dateSelection == DateSelection.DATE_PICKER)
            DatePicker(state = datePickerState)
        else
            TimePicker(state = timePickerState)

    }
}
enum class DateSelection{
    DATE_PICKER,
    TIME_PICKER
}

@Preview
@Composable
fun DatePreview(){
    DateTimePicker {

    }
}