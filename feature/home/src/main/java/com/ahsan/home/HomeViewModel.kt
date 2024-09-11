package com.ahsan.home

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.domain.appointment.GetAppointmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAppointmentsUseCase: GetAppointmentsUseCase): BaseViewModel<ViewState, HomeEvent>() {

    override fun onTriggerEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetAppointments -> getAppointments()
        }
    }

    private fun getAppointments() {
        viewModelScope.launch {
            updateState(ViewState(getAppointmentsUseCase()))
        }
    }
}