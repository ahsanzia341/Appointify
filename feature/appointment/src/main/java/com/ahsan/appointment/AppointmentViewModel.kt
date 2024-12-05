package com.ahsan.appointment

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Client
import com.ahsan.data.models.ServiceAndCurrency
import com.ahsan.domain.appointment.FindByIdAppointmentsUseCase
import com.ahsan.domain.appointment.PostAppointmentUseCase
import com.ahsan.domain.appointment.UpdateAppointmentUseCase
import com.ahsan.domain.client.GetClientsUseCase
import com.ahsan.domain.service.GetServicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val postAppointmentUseCase: PostAppointmentUseCase,
    private val updateAppointmentUseCase: UpdateAppointmentUseCase,
    private val findByIdAppointmentsUseCase: FindByIdAppointmentsUseCase,
    private val getServicesUseCase: GetServicesUseCase,
    private val getClientsUseCase: GetClientsUseCase): BaseViewModel<ViewState, AppointmentEvent>() {

    override fun onTriggerEvent(event: AppointmentEvent) {
        when (event) {
            is AppointmentEvent.PostAppointment -> postAppointment(event.appointment, event.services)
            is AppointmentEvent.UpdateAppointment -> updateAppointment(event.appointment, event.services)
            is AppointmentEvent.FindById -> findById(event.id)
            AppointmentEvent.OnFail -> updateState(ViewState(services = services, clients = clients))
        }
    }

    private var clients: List<Client>? = null
    private var services: List<ServiceAndCurrency>? = null

    init {
        getAllServicesAndClients()
    }

    private fun postAppointment(appointment: Appointment, services: List<Int>) {
        viewModelScope.launch {
            if(validateForm(appointment, services))
                postAppointmentUseCase(appointment, services)
        }
    }

    private fun validateForm(appointment: Appointment, services: List<Int>): Boolean {
        val isValidated = appointment.title.isNotEmpty() && (appointment.startDate?.time
            ?: 0L) > 0 && appointment.clientId > 0 && services.isNotEmpty()
        updateState(ViewState(isFormValidated = Pair(isValidated, !isValidated), services = this.services, clients = clients))
        return isValidated
    }

    private fun updateAppointment(appointment: Appointment, services: List<Int>){
        if(validateForm(appointment, services)){
            viewModelScope.launch {
                updateAppointmentUseCase(appointment)
            }
        }
    }

    private fun getAllServicesAndClients(){
        viewModelScope.launch {
            services = getServicesUseCase()
            clients = getClientsUseCase()
            updateState(ViewState(services = services, clients = clients))
        }
    }

    private fun findById(id: Int){
        viewModelScope.launch {
            updateState(ViewState(appointment = findByIdAppointmentsUseCase(id)))
        }
    }
}