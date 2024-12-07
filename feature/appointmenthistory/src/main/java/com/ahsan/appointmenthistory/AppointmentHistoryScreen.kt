package com.ahsan.appointmenthistory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.R
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.core.AppRoute.AppointmentDetailRoute
import com.ahsan.core.extension.toEasyFormat
import com.ahsan.data.models.AppointmentAndClient
import com.ahsan.data.models.AppointmentStatus

@Composable
fun AppointmentHistoryScreen(navController: NavController) {
    val viewModel = hiltViewModel<AppointmentHistoryViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    AppointmentHistoryUI(list = viewState?.appointments ?: listOf()){
        navController.navigate(AppointmentDetailRoute(it))
    }
}

@Composable
fun AppointmentHistoryUI(list: List<AppointmentAndClient>, onItemClick: (Int) -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.appointment_history), navIcon = null)
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)){
            LazyColumn(Modifier.fillMaxSize()) {
                items(list){
                    AppointmentRow(appointmentAndClient = it) {
                        onItemClick(it.appointment.appointmentId)
                    }
                }
            }
            if (list.isEmpty())
                ThemeText(
                    text = stringResource(id = R.string.no_history_appointments),
                    modifier = Modifier.align(Alignment.Center)
                )
        }
    }

}

@Composable
fun AppointmentRow(appointmentAndClient: AppointmentAndClient, onClick: () -> Unit) {
    val appointment = appointmentAndClient.appointment
    val status = if(appointment.status == AppointmentStatus.NOT_STARTED) AppointmentStatus.ENDED.name else appointment.status.name
    Card(modifier = Modifier.padding(8.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick()
            }
            .fillMaxWidth()) {
            ThemeText(text = appointmentAndClient.appointment.title)
            ThemeText(text = appointmentAndClient.client.name)
            ThemeText(text = appointmentAndClient.appointment.startDate?.toEasyFormat() ?: "")
            ThemeText(text = status)
        }
    }

}

@Composable
@Preview
fun AppointmentHistoryPreview(){
    AppointmentHistoryUI(listOf()){}
}