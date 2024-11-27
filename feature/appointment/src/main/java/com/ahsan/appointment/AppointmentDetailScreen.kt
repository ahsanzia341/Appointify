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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeHeaderText
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute
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
    AppointmentDetailUI(viewState?.appointment ?: AppointmentAndClient(Appointment(), Client(name = "", phoneNumber = "")), onCancelPress = {
        viewModel.onTriggerEvent(AppointmentEvent.UpdateAppointment(it))
    }, onUpdatePress = {
        navController.navigate(DestinationRoute.UPDATE_APPOINTMENT_ROUTE.replace("{${DestinationRoute.PassedKey.ID}}", it.id.toString()))
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
            ThemeText(text = appointment.notes)

            ThemeButton(text = "Cancel Appointment") {
                appointment.status = AppointmentStatus.CANCELED
                onCancelPress(appointment)
                onBackPressed()
            }
            ThemeButton(text = "Update Appointment") {
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
    AppointmentDetailUI(AppointmentAndClient(Appointment(0, "New Appointment"), Client(name = "Test", phoneNumber = "1234")), {}, {}){

    }
}