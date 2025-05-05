package com.ahsan.appointment

import com.ahsan.data.models.Appointment
import com.ahsan.data.models.AppointmentAndClient
import com.ahsan.data.models.Client
import com.ahsan.data.models.ServiceAndCurrency

data class ViewState(val appointments: List<AppointmentAndClient>? = null,
                     val clients: List<Client>? = null, val appointment: AppointmentAndClient? = null, val services: List<ServiceAndCurrency>? = null,
                     val selectedServices: List<ServiceAndCurrency>? = null, val isFormValidated: Pair<Boolean, Boolean>? = null)

sealed class AppointmentEvent {
    data class PostAppointment(val appointment: Appointment, val services: List<Int>): AppointmentEvent()
    data class UpdateAppointment(val appointment: Appointment, val services: List<Int>): AppointmentEvent()
    data object OnFail: AppointmentEvent()
}
