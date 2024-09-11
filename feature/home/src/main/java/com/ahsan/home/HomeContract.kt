package com.ahsan.home

import com.ahsan.data.models.AppointmentAndClient

data class ViewState(val appointments: List<AppointmentAndClient>)

sealed class HomeEvent {
    data object GetAppointments: HomeEvent()
}
