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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ConfirmationDialog
import com.ahsan.composable.R
import com.ahsan.composable.ThemeCard
import com.ahsan.composable.ThemeFloatingActionButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.core.AppRoute.AppointmentDetailRoute
import com.ahsan.core.AppRoute.CreateAppointmentRoute
import com.ahsan.core.AppRoute.CreateClientRoute
import com.ahsan.core.AppRoute.ServiceCreateRoute
import com.ahsan.core.extension.toEasyFormat
import com.ahsan.data.models.AppointmentAndClient
import com.ahsan.home.calendarview.CalendarView

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(HomeEvent.GetUpcomingAppointments)
    }
    var showServiceDialog by remember {
        mutableStateOf(false)
    }
    var showClientDialog by remember {
        mutableStateOf(false)
    }
    HomeUI(viewState?.appointments ?: listOf(), onAddClicked = {
        if (viewState?.clientCount == 0) {
            showClientDialog = true
        } else if (viewState?.serviceCount == 0) {
            showServiceDialog = true
        } else {
            navController.navigate(CreateAppointmentRoute)
        }
    }) {
        navController.navigate(
            AppointmentDetailRoute(it)
        )
    }
    if(showClientDialog){
        ConfirmationDialog(
            title = stringResource(R.string.no_clients),
            text = "Please add at least one client to schedule an appointment",
            onDismissRequest = { showClientDialog = false }) {
            navController.navigate(CreateClientRoute)
            showClientDialog = false
        }
    }
    if(showServiceDialog){
        ConfirmationDialog(
            title = stringResource(R.string.no_services),
            text = "Please add at least one service to schedule an appointment",
            onDismissRequest = { showServiceDialog = false }) {
            navController.navigate(ServiceCreateRoute)
            showServiceDialog = false
        }
    }
}

@Composable
fun HomeUI(list: List<AppointmentAndClient>, onAddClicked: () -> Unit, onItemClick: (Int) -> Unit) {
    val calendarView by remember {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        Column {
            TopBar(title = stringResource(id = R.string.upcoming_appointments), navIcon = null)
            /*ThemeSwitch(Modifier.align(Alignment.End),"CalendarView/ListView") {
                calendarView = it
            }*/
        }

    }, modifier = Modifier.padding(8.dp), bottomBar = {
        Column(Modifier.fillMaxWidth()) {
            ThemeFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            ) {
                onAddClicked()
            }
        }
    }) { padding ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {


            if (list.isEmpty())
                ThemeText(
                    text = stringResource(id = R.string.no_appointments),
                    modifier = Modifier.align(Alignment.Center)
                )
            if(calendarView){
                CalendarView()
            }
            else{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(list) { item ->
                        AppointmentRow(item) {
                            onItemClick(item.appointment.appointmentId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentRow(appointmentAndClient: AppointmentAndClient, onClick: () -> Unit) {
    ThemeCard {
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

@Preview
@Composable
fun Preview(){
    SmartAppointmentTheme {
        HomeUI(listOf(), {}) { }
    }
}