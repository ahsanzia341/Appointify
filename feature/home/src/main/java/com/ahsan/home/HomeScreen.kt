package com.ahsan.home

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
import com.ahsan.composable.R
import com.ahsan.composable.ThemeFloatingActionButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute
import com.ahsan.core.extension.toEasyFormat
import com.ahsan.data.models.AppointmentAndClient

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(HomeEvent.GetAppointments)
    }
    HomeUI(viewState?.appointments ?: listOf(), onAddClicked = {
        navController.navigate(DestinationRoute.CREATE_APPOINTMENT_ROUTE)
    }){
        navController.navigate(DestinationRoute.APPOINTMENT_DETAIL_ROUTE.replace("{${DestinationRoute.PassedKey.ID}}", it.toString()))
    }
}

@Composable
fun HomeUI(list: List<AppointmentAndClient>, onAddClicked: () -> Unit, onItemClick: (Int) -> Unit) {
    Scaffold(topBar = {
        TopBar(title = "Upcoming appointments", navIcon = null)
    }, modifier = Modifier.padding(8.dp)) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            if (list.isEmpty())
                ThemeText(
                    text = stringResource(id = R.string.no_appointments),
                    modifier = Modifier.align(Alignment.Center)
                )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(list) { item ->
                    AppointmentRow(item) {
                        onItemClick(item.appointment.id)
                    }
                }
            }
            ThemeFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                onAddClicked()
            }
        }
    }

}

@Composable
fun AppointmentRow(appointmentAndClient: AppointmentAndClient, onClick: () -> Unit) {
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
        }
    }
}