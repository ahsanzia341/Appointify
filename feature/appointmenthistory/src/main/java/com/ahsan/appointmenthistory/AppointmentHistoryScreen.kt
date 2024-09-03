package com.ahsan.appointmenthistory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahsan.composable.TopBar
import com.ahsan.data.models.AppointmentAndClient

@Composable
fun AppointmentHistoryScreen() {
    val viewModel = hiltViewModel<AppointmentHistoryViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    AppointmentHistoryUI(list = viewState?.appointments ?: listOf())
}

@Composable
fun AppointmentHistoryUI(list: List<AppointmentAndClient>){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.appointment_history), navIcon = null)
    }) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            LazyColumn(Modifier.fillMaxSize()) {
                items(list){

                }
            }
        }
    }

}

@Composable
@Preview
fun AppointmentHistoryPreview(){
    AppointmentHistoryUI(listOf())
}