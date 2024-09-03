package com.ahsan.composable

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
import androidx.compose.ui.res.stringResource
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(onDismiss: (Long?) -> Unit) {
    val calendar by remember {
        mutableStateOf(Calendar.getInstance())
    }
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= System.currentTimeMillis()
        }

        override fun isSelectableYear(year: Int): Boolean {
            return year >= calendar.get(Calendar.YEAR)
        }
    })
    val timePickerState =
        rememberTimePickerState(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
    val selectedDateMillis = datePickerState.selectedDateMillis
    if (selectedDateMillis != null)
        calendar.time = Date(selectedDateMillis)
    if (selectedDateMillis == null) {
        DatePickerDialog(
            onDismissRequest = {
                onDismiss(null)
            },
            confirmButton = {
                TextButton(onClick = {
                    onDismiss(null)
                }) {
                    ThemeText(text = stringResource(id = R.string.dismiss))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    } else {
        DatePickerDialog(
            onDismissRequest = {
                onDismiss(null)
            },
            confirmButton = {
                TextButton(onClick = {
                    calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    calendar.set(Calendar.MINUTE, timePickerState.minute)
                    onDismiss(calendar.time.time)
                }) {
                    ThemeText(text = stringResource(id = R.string.dismiss))
                }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}