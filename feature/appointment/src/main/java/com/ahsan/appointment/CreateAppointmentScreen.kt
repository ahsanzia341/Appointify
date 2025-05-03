package com.ahsan.appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.DisabledTextField
import com.ahsan.composable.InfoDialog
import com.ahsan.composable.RequiredTextField
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeDatePicker
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Client
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency
import java.util.Date

@Composable
fun CreateAppointmentScreen(navController: NavController, id: Int = 0) {
    val viewModel = hiltViewModel<AppointmentViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        if(id != 0){
            viewModel.onTriggerEvent(AppointmentEvent.FindById(id))
        }
    }
    CreateAppointmentUI(Appointment(
        clientId = 0,
        title = "",
        startDate = null,
        endDate = null,
        location = "",
    ), viewState?.clients ?: listOf(),
        isShowDialog = viewState?.isFormValidated ?: Pair(false, false),
        serviceAndCurrency = viewState?.services ?: listOf(), onCreate = { appointment, services ->
            viewModel.onTriggerEvent(AppointmentEvent.PostAppointment(appointment,
                services.map { obj -> obj.serviceId }))
        }, onFail = {
            viewModel.onTriggerEvent(AppointmentEvent.OnFail)
        }) {
        navController.popBackStack()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAppointmentUI(appointmentObject: Appointment, clients: List<Client>, serviceAndCurrency: List<ServiceAndCurrency>,
                        isShowDialog: Pair<Boolean, Boolean>, onCreate: (Appointment, List<Service>) -> Unit, onFail: () -> Unit,
                        onBackPressed: () -> Unit) {
    Scaffold(modifier = Modifier.padding(8.dp),
        topBar = {
            TopBar(
                title = stringResource(id = com.ahsan.composable.R.string.create_appointment),
                onClickNavIcon = {
                    onBackPressed()
                })
        }
    ) { padding ->
        var appointment by remember {
            mutableStateOf(appointmentObject)
        }
        var showClientSelectBottomSheet by remember {
            mutableStateOf(false)
        }
        var client by remember {
            mutableStateOf(clients.find { it.id == appointment.clientId })
        }
        var services by remember {
            mutableStateOf(listOf<Service>())
        }
        var showServicesBottomSheet by remember {
            mutableStateOf(false)
        }
        val clientSelectBottomSheetState =
            rememberModalBottomSheetState(skipPartiallyExpanded = false)
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
                RequiredTextField(label = stringResource(com.ahsan.composable.R.string.title)){
                    appointment = appointment.copy(title = it)
                }
                ThemeDatePicker(
                    label = stringResource(id = com.ahsan.composable.R.string.start_date),
                    errorMessage = if (appointment.startDate == null) stringResource(
                        id = com.ahsan.composable.R.string.field_required,
                        stringResource(id = com.ahsan.composable.R.string.start_date)
                    ) else ""
                ) {
                    appointment = appointment.copy(startDate = it)
                }
                ThemeDatePicker(label = stringResource(id = com.ahsan.composable.R.string.end_date)) {
                    appointment = appointment.copy(endDate = it)
                }
                DisabledTextField(
                    stringResource(id = com.ahsan.composable.R.string.client),
                    if (client == null) stringResource(
                        id = com.ahsan.composable.R.string.field_required,
                        stringResource(id = com.ahsan.composable.R.string.client)
                    ) else "",
                    client?.name ?: ""
                ) {
                    showClientSelectBottomSheet = true
                }
                DisabledTextField(
                    stringResource(id = com.ahsan.composable.R.string.services),
                    if (services.isEmpty()) "At least one service is required" else "",
                    "${services.size} services added"
                ) {
                    showServicesBottomSheet = true
                }
                ThemeTextField(label = stringResource(id = com.ahsan.composable.R.string.location)) {
                    appointment = appointment.copy(location = it)
                }
                ThemeTextField(
                    label = stringResource(id = com.ahsan.composable.R.string.notes),
                    imeAction = ImeAction.Done
                ) {
                    appointment = appointment.copy(notes = it)
                }
                ThemeButton(
                    text = stringResource(id = com.ahsan.composable.R.string.create),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onCreate(
                        appointment,
                        services
                    )
                }
                if (isShowDialog.first) {
                    InfoDialog(
                        title = stringResource(id = com.ahsan.composable.R.string.success),
                        text = stringResource(id = com.ahsan.composable.R.string.appointment_created)
                    ) {
                        onBackPressed()
                    }
                } else if (isShowDialog.second) {
                    InfoDialog(
                        title = stringResource(id = com.ahsan.composable.R.string.failure),
                        text = stringResource(id = com.ahsan.composable.R.string.required_fields_missing)
                    ) {
                        onFail()
                    }
                }
            }
        }
        if (showServicesBottomSheet) {
            ModalBottomSheet(onDismissRequest = { showServicesBottomSheet = false }) {
                ServicesBottomSheet(services = serviceAndCurrency) {
                    services = it.map { obj -> obj.service }
                    showServicesBottomSheet = false
                }
            }
        }
        if (showClientSelectBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showClientSelectBottomSheet = false },
                sheetState = clientSelectBottomSheetState
            ) {
                ClientSelectScreen(clients) {
                    appointment = appointment.copy(clientId = it.id)
                    client = it
                    showClientSelectBottomSheet = false
                }
            }
        }
    }
}

@Composable
@Preview
fun CreatePreview(){
    SmartAppointmentTheme {
        CreateAppointmentUI(Appointment(clientId = 1, title = "", startDate = null, endDate = Date(), location = ""),
            listOf(Client(1, "Test Client", "12345")), isShowDialog = Pair(false, false),
            serviceAndCurrency = listOf(), onCreate = {_, _ -> }, onFail = {}){}
    }
}