package com.ahsan.appointment

import com.ahsan.data.models.Appointment
import com.ahsan.data.models.AppointmentAndClient
import com.ahsan.data.models.Client
import com.ahsan.data.models.ServiceAndCurrency

data class ViewState(val appointments: List<AppointmentAndClient>? = null,
    val client: Client? = null, val appointment: AppointmentAndClient? = null, val services: List<ServiceAndCurrency>? = null,
    val isFormValidated: Pair<Boolean, Boolean>? = null)

sealed class AppointmentEvent {
    data class PostAppointment(val appointment: Appointment): AppointmentEvent()
    data class UpdateAppointment(val appointment: Appointment): AppointmentEvent()
    data class FindById(val id: Int): AppointmentEvent()
    data class FindClientById(val id: Int): AppointmentEvent()
    data class FindServicesById(val list: List<Int>): AppointmentEvent()
    data class ValidateForm(val appointment: Appointment): AppointmentEvent()
    data object OnFail: AppointmentEvent()
}
