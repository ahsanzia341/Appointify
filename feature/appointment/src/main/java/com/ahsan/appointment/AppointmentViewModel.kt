package com.ahsan.appointment

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Appointment
import com.ahsan.domain.appointment.FindByIdAppointmentsUseCase
import com.ahsan.domain.appointment.PostAppointmentUseCase
import com.ahsan.domain.appointment.UpdateAppointmentUseCase
import com.ahsan.domain.client.GetClientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val postAppointmentUseCase: PostAppointmentUseCase,
    private val updateAppointmentUseCase: UpdateAppointmentUseCase,
    private val findByIdAppointmentsUseCase: FindByIdAppointmentsUseCase,
    private val getClientsUseCase: GetClientsUseCase): BaseViewModel<ViewState, AppointmentEvent>() {

    override fun onTriggerEvent(event: AppointmentEvent) {
        when (event) {
            is AppointmentEvent.PostAppointment -> postAppointment(event.appointment)
            is AppointmentEvent.UpdateAppointment -> updateAppointment(event.appointment)
            is AppointmentEvent.FindById -> findById(event.id)
            AppointmentEvent.GetClients -> getClients()
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
            updateAppointmentUseCase(appointment)
        }
    }

    private fun findById(id: Int){
        viewModelScope.launch {
            updateState(ViewState(appointment = findByIdAppointmentsUseCase(id)))
        }
    }
    private fun getClients(){
        viewModelScope.launch {
            updateState(ViewState(clients = getClientsUseCase()))
        }
    }
}