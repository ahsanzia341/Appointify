package com.ahsan.home.calendarview

import com.ahsan.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(): BaseViewModel<ViewState, CalendarViewEvent>() {
    override fun onTriggerEvent(event: CalendarViewEvent) {

    }

    init {
        val month = calendar.getDisplayName(
            Calendar.MONTH, Calendar.LONG,
            Locale.getDefault())
        updateState(ViewState(selectedMonth = month ?: "", selectedYear = calendar.get(Calendar.YEAR),
            calendar.firstDayOfWeek, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)))
    }
}