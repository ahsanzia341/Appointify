package com.ahsan.appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeHeaderText
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.core.AppRoute
import com.ahsan.core.extension.toEasyFormat
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.AppointmentAndClient
import com.ahsan.data.models.AppointmentStatus
import com.ahsan.data.models.Client

@Composable
fun AppointmentDetailScreen(navController: NavController, id: Int) {
    val viewModel = hiltViewModel<AppointmentViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(AppointmentEvent.FindById(id))
    }
    AppointmentDetailUI(viewState?.appointment ?: AppointmentAndClient(Appointment(), Client(name = "", phoneNumber = ""), listOf()), onCancelPress = {
        viewModel.onTriggerEvent(AppointmentEvent.UpdateAppointment(it, listOf()))
    }, onUpdatePress = {
        navController.navigate(AppRoute.CreateAppointmentRoute)
    }){
        navController.popBackStack()
    }
}

@Composable
fun AppointmentDetailUI(appointmentAndClient: AppointmentAndClient, onUpdatePress: (Appointment) -> Unit, onCancelPress: (Appointment) -> Unit, onBackPressed: () -> Unit) {
    val appointment = appointmentAndClient.appointment
    val client = appointmentAndClient.client
    Scaffold(topBar = {
        TopBar(title = appointment.title, onClickNavIcon = {
            onBackPressed()
        })
    }, modifier = Modifier.padding(8.dp)) {
        Column(Modifier.padding(it), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            RowField("Start Date:", appointment.startDate?.toEasyFormat() ?: "")
            RowField("End Date:", appointment.endDate?.toEasyFormat() ?: "")
            RowField("Client name:", client.name)
            RowField("Client phone:", client.phoneNumber)
            ThemeText(text = appointment.location)
            ThemeText(text = appointment.notes)

            ThemeButton(text = stringResource(com.ahsan.composable.R.string.cancel_appointment)) {
                appointment.status = AppointmentStatus.CANCELED
                onCancelPress(appointment)
                onBackPressed()
            }
            ThemeButton(text = stringResource(com.ahsan.composable.R.string.update_appointment)) {
                onUpdatePress(appointment)
            }
        }
    }
}

@Composable
fun RowField(label: String, value: String){
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ThemeHeaderText(text = label)
        ThemeText(text = value)
    }
}

@Composable
@Preview
fun AppointmentDetailPreview(){
    SmartAppointmentTheme {
        AppointmentDetailUI(AppointmentAndClient(Appointment(0, "New Appointment"), Client(name = "Test", phoneNumber = "1234"), services = listOf()), {}, {}){

        }
    }
}