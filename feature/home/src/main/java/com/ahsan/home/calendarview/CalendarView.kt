package com.ahsan.home.calendarview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahsan.composable.ThemeText
import com.ahsan.composable.theme.SmartAppointmentTheme

@Composable
fun CalendarView() {
    val viewModel = hiltViewModel<CalendarViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    CalendarUI(viewState?.selectedMonth, viewState?.selectedYear, 2)
}

@Composable
fun CalendarUI(selectedMonth: String?, selectedYear: Int?, firstDayOfWeek: Int){
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ThemeText(text = "$selectedMonth $selectedYear")
        Row(
            Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach {
                ThemeText(
                    text = it,
                    modifier = Modifier,
                )
            }
        }
        
        for (i in 0..4) {
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (k in 0..6) {
                    if(firstDayOfWeek == k){
                        ThemeText(modifier = Modifier, text = (i * 7 + k + 1).toString())
                    }
                    else{
                        ThemeText(modifier = Modifier, text = (i * 7 + k + 1).toString())

                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CalendarPreview(){
    SmartAppointmentTheme {
        val state = rememberDatePickerState()
        //DatePicker(state)
        CalendarUI("April", 2025, 2)
    }
}