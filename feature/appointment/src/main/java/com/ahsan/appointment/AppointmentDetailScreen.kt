package com.ahsan.appointment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.TopBar
import com.ahsan.data.models.Appointment

@Composable
fun AppointmentDetailScreen(navController: NavController, id: Int) {
    val viewModel = hiltViewModel<AppointmentViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(AppointmentEvent.FindById(id))
    }
    AppointmentDetailUI(viewState?.appointment?.appointment ?: Appointment()){
        navController.popBackStack()
    }
}

@Composable
fun AppointmentDetailUI(appointment: Appointment, onBackPressed: () -> Unit) {
    Scaffold(topBar = {
        TopBar(title = appointment.title, onClickNavIcon = {
            onBackPressed()
        })
    }) {
        Column(Modifier.padding(it)) {
            ThemeButton(text = "Cancel Appointment") {

            }
            ThemeButton(text = "Update Appointment") {

            }
        }
    }
}

@Composable
@Preview
fun AppointmentDetailPreview(){
    AppointmentDetailUI(Appointment()){

    }
}