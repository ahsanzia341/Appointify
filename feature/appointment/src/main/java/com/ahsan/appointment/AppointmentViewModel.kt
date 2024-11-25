package com.ahsan.appointment

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Client
import com.ahsan.data.models.ServiceAndCurrency
import com.ahsan.domain.appointment.FindByIdAppointmentsUseCase
import com.ahsan.domain.appointment.PostAppointmentUseCase
import com.ahsan.domain.appointment.UpdateAppointmentUseCase
import com.ahsan.domain.client.FindClientByIdUseCase
import com.ahsan.domain.service.GetServicesByIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val postAppointmentUseCase: PostAppointmentUseCase,
    private val updateAppointmentUseCase: UpdateAppointmentUseCase,
    private val findByIdAppointmentsUseCase: FindByIdAppointmentsUseCase,
    private val getServicesByIdsUseCase: GetServicesByIdsUseCase,
    private val findClientByIdUseCase: FindClientByIdUseCase): BaseViewModel<ViewState, AppointmentEvent>() {

    override fun onTriggerEvent(event: AppointmentEvent) {
        when (event) {
            is AppointmentEvent.PostAppointment -> postAppointment(event.appointment)
            is AppointmentEvent.UpdateAppointment -> updateAppointment(event.appointment)
            is AppointmentEvent.FindById -> findById(event.id)
            is AppointmentEvent.FindClientById -> findClientById(event.id)
            is AppointmentEvent.FindServicesById -> findServicesById(event.list)
            is AppointmentEvent.ValidateForm -> validateForm(event.appointment)
        }
    }
    private var selectedClient: Client? = null
    private var selectedServices: List<ServiceAndCurrency>? = null

    private fun postAppointment(appointment: Appointment) {
        viewModelScope.launch {
            if(validateForm(appointment))
                postAppointmentUseCase(appointment)
        }
    }

    private fun validateForm(appointment: Appointment): Boolean {
        return appointment.title.isNotEmpty() && appointment.startDate.time > 0 && appointment.clientId > 0 && appointment.services.isNotEmpty()
    }

    private fun updateAppointment(appointment: Appointment){
        if(appointment.title.isNotEmpty() && appointment.startDate.time > 0 && appointment.clientId > 0){
            viewModelScope.launch {
                updateAppointmentUseCase(appointment)
            }
        }
    }

    private fun findServicesById(list: List<Int>){
        viewModelScope.launch {
            selectedServices = getServicesByIdsUseCase(list)
            updateState(ViewState(services = selectedServices, client = selectedClient))
        }
    }

    private fun findById(id: Int){
        viewModelScope.launch {
            updateState(ViewState(appointment = findByIdAppointmentsUseCase(id)))
        }
    }

    private fun findClientById(id: Int){
        viewModelScope.launch {
            selectedClient = findClientByIdUseCase(id)
            updateState(ViewState(client = selectedClient, services = selectedServices))
        }
    }
}