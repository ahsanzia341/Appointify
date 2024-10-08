package com.ahsan.appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.ahsan.composable.InfoDialog
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeDatePicker
import com.ahsan.composable.ThemeText
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Client
import java.util.Date

@Composable
fun CreateAppointmentScreen(navController: NavController) {
    val viewModel = hiltViewModel<AppointmentViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    val clientId = navController.currentBackStackEntry?.savedStateHandle?.get<Int>(DestinationRoute.PassedKey.CLIENT_ID) ?: 0
    val serviceIds = navController.currentBackStackEntry?.savedStateHandle?.get<String>(DestinationRoute.PassedKey.SERVICE_IDS) ?: 0
    LaunchedEffect(clientId) {
        viewModel.onTriggerEvent(AppointmentEvent.FindClientById(clientId))
    }
    CreateAppointmentUI(Appointment(
        clientId = clientId,
        title = "",
        startDate = Date(),
        endDate = Date(),
        location = "",
        services = listOf()
    ), viewState?.client,
        clientAddClick = {
            navController.navigate(DestinationRoute.SELECT_CLIENT_ROUTE)
        }, servicesAddClick = {
            navController.navigate(DestinationRoute.SERVICE_SELECT_ROUTE)
        }, onCreate = {
            viewModel.onTriggerEvent(AppointmentEvent.PostAppointment(it))
        }) {
        navController.popBackStack()
    }
}

@Composable
fun CreateAppointmentUI(appointment: Appointment, client: Client?, servicesAddClick: () -> Unit, clientAddClick: () -> Unit, onCreate: (Appointment) -> Unit, onBackPressed: () -> Unit) {
    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            TopBar(
                title = stringResource(id = com.ahsan.composable.R.string.create_appointment),
                onClickNavIcon = {
                    onBackPressed()
                })
        }
    ) { padding ->
        var title by remember {
            mutableStateOf(appointment.title)
        }
        var location by remember {
            mutableStateOf(appointment.location)
        }
        var startDate by remember {
            mutableStateOf(appointment.startDate)
        }
        var endDate by remember {
            mutableStateOf(appointment.endDate)
        }
        /*val clientId by remember {
            mutableIntStateOf(appointment.clientId)
        }*/
        val clientId = appointment.clientId
        val services by remember {
            mutableStateOf(appointment.services)
        }
        var showSuccessDialog by remember {
            mutableStateOf(false)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                if(client != null) {
                    ThemeText(text = "Client:")
                    ThemeText(text = client.name)
                    ThemeText(text = client.phoneNumber)
                }
                else{
                    ThemeButton(text = "Add a client") {
                        clientAddClick()
                    }
                }
                ThemeText(text = "Service:")
                ThemeButton(text = "Add services") {
                    servicesAddClick()
                }
                ThemeTextField(
                    label = stringResource(id = com.ahsan.composable.R.string.title),
                ) {
                    title = it
                }
                ThemeDatePicker(label = stringResource(id = com.ahsan.composable.R.string.start_date)) {
                    startDate = it
                }
                ThemeDatePicker(label = stringResource(id = com.ahsan.composable.R.string.end_date)) {
                    endDate = it
                }
                ThemeTextField(label = stringResource(id = com.ahsan.composable.R.string.location)) {
                    location = it
                }
                ThemeTextField(label = stringResource(id = com.ahsan.composable.R.string.notes)) {
                    location = it
                }
                ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.create)) {
                    onCreate(
                        Appointment(
                            title = title,
                            startDate = startDate,
                            endDate = endDate,
                            location = location,
                            clientId = clientId,
                            services = services
                        )
                    )
                    showSuccessDialog = true
                }
                if (showSuccessDialog) {
                    InfoDialog(
                        title = stringResource(id = com.ahsan.composable.R.string.success),
                        text = "Appointment created"
                    ) {
                        onBackPressed()
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun CreatePreview(){
    CreateAppointmentUI(Appointment(clientId = 0, title = "", startDate = Date(), endDate = Date(), location = "", services = listOf()), null, clientAddClick = {}, servicesAddClick = {},  onCreate = {}){}
}