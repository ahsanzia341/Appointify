package com.ahsan.appointment

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Appointment
import com.ahsan.domain.appointment.CancelAppointmentUseCase
import com.ahsan.domain.appointment.FindByIdAppointmentsUseCase
import com.ahsan.domain.appointment.PostAppointmentUseCase
import com.ahsan.domain.appointment.UpdateAppointmentUseCase
import com.ahsan.domain.client.FindClientByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val postAppointmentUseCase: PostAppointmentUseCase,
    private val updateAppointmentUseCase: UpdateAppointmentUseCase,
    private val findByIdAppointmentsUseCase: FindByIdAppointmentsUseCase,
    private val cancelAppointmentUseCase: CancelAppointmentUseCase,
    private val findClientByIdUseCase: FindClientByIdUseCase): BaseViewModel<ViewState, AppointmentEvent>() {

    override fun onTriggerEvent(event: AppointmentEvent) {
        when (event) {
            is AppointmentEvent.PostAppointment -> postAppointment(event.appointment)
            is AppointmentEvent.UpdateAppointment -> updateAppointment(event.appointment)
            is AppointmentEvent.FindById -> findById(event.id)
            is AppointmentEvent.FindClientById -> findClientById(event.id)
        }
    }

    private fun postAppointment(appointment: Appointment) {
        if(appointment.title.isNotEmpty() && appointment.startDate.time > 0 && appointment.clientId > 0){
            viewModelScope.launch {
                postAppointmentUseCase(appointment)
            }
        }
    }

    private fun updateAppointment(appointment: Appointment){
        if(appointment.title.isNotEmpty() && appointment.startDate.time > 0 && appointment.clientId > 0){
            viewModelScope.launch {
                updateAppointmentUseCase(appointment)
            }
        }
    }

    private fun cancel(appointment: Appointment){
        viewModelScope.launch {
            cancelAppointmentUseCase(appointment)
        }
    }

    private fun findById(id: Int){
        viewModelScope.launch {
            updateState(ViewState(appointment = findByIdAppointmentsUseCase(id)))
        }
    }
    private fun findClientById(id: Int){
        viewModelScope.launch {
            updateState(ViewState(client = findClientByIdUseCase(id)))
        }
    }
}