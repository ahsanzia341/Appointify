package com.ahsan.home.calendarview

import java.util.Calendar

val calendar: Calendar = Calendar.getInstance()
data class ViewState(val selectedMonth: String = "", val selectedYear: Int = calendar.get(Calendar.YEAR),
                     val firstDayOfWeek: Int = calendar.get(Calendar.DAY_OF_WEEK), val lastDay: Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH))

sealed class CalendarViewEvent{

}