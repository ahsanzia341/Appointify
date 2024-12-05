package com.ahsan.home

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.domain.appointment.GetAppointmentsUseCase
import com.ahsan.domain.client.GetClientCountUseCase
import com.ahsan.domain.service.GetServiceCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAppointmentsUseCase: GetAppointmentsUseCase,
    private val getClientCountUseCase: GetClientCountUseCase,
    private val getServiceCountUseCase: GetServiceCountUseCase): BaseViewModel<ViewState, HomeEvent>() {

    override fun onTriggerEvent(event: HomeEvent) {
        when(event){
            HomeEvent.GetUpcomingAppointments -> getAppointments()
        }
    }

    private fun getAppointments() {
        viewModelScope.launch {
            updateState(ViewState(getAppointmentsUseCase(), clientCount = getClientCountUseCase(), serviceCount = getServiceCountUseCase()))
        }
    }
}