package com.ahsan.home

import com.ahsan.data.models.AppointmentAndClient

data class ViewState(val appointments: List<AppointmentAndClient>, val clientCount: Int = 0, val serviceCount: Int = 0)

sealed class HomeEvent{
    data object GetUpcomingAppointments: HomeEvent()
}
