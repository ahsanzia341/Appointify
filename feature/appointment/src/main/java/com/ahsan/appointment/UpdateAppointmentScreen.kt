package com.ahsan.appointment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.data.models.Appointment

@Composable
fun UpdateAppointmentScreen(navController: NavController, appointmentId: Int) {
    val viewModel = hiltViewModel<AppointmentViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(true) {
        viewModel.onTriggerEvent(AppointmentEvent.FindById(appointmentId))
    }
    CreateAppointmentUI(viewState?.appointment?.appointment ?: Appointment(), clientAddClick = {}, servicesAddClick = {}, onCreate = {
        viewModel.onTriggerEvent(AppointmentEvent.UpdateAppointment(it))
    }){
        navController.popBackStack()
    }
}