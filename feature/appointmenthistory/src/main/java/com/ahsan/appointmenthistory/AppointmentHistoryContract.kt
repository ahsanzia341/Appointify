package com.ahsan.appointmenthistory

import com.ahsan.data.models.AppointmentAndClient

data class ViewState(val appointments: List<AppointmentAndClient>? = null)

sealed class AppointmentHistoryEvent {
    data object GetAppointmentHistory: AppointmentHistoryEvent()
}
