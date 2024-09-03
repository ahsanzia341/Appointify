package com.ahsan.appointment

import com.ahsan.data.models.Appointment
import com.ahsan.data.models.AppointmentAndClient
import com.ahsan.data.models.Client

data class ViewState(val appointments: List<AppointmentAndClient>? = null,
    val clients: List<Client>? = null, val appointment: AppointmentAndClient? = null)

sealed class AppointmentEvent {
    data class PostAppointment(val appointment: Appointment): AppointmentEvent()
    data class UpdateAppointment(val appointment: Appointment): AppointmentEvent()
    data class FindById(val id: Int): AppointmentEvent()
    data object GetClients: AppointmentEvent()
}
