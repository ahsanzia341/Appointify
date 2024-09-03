package com.ahsan.appointmenthistory

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.domain.appointment.GetAppointmentHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentHistoryViewModel @Inject constructor(
    private val getAppointmentHistoryUseCase: GetAppointmentHistoryUseCase): BaseViewModel<ViewState, AppointmentHistoryEvent>() {

    override fun onTriggerEvent(event: AppointmentHistoryEvent) {
        when (event) {
            AppointmentHistoryEvent.GetAppointmentHistory -> TODO()
        }
    }

    init {
        getHistoryAppointments()
    }

    private fun getHistoryAppointments() {
        viewModelScope.launch {
            updateState(ViewState(appointments = getAppointmentHistoryUseCase()))
        }
    }
}