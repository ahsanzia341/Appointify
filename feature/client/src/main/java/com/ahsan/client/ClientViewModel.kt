package com.ahsan.client

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Appointment
import com.ahsan.data.models.Client
import com.ahsan.domain.appointment.PostAppointmentUseCase
import com.ahsan.domain.client.GetClientsUseCase
import com.ahsan.domain.client.PostClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val postClientUseCase: PostClientUseCase,
    private val getClientsUseCase: GetClientsUseCase): BaseViewModel<ViewState, ClientEvent>() {

    private var clients: List<Client> = listOf()

    override fun onTriggerEvent(event: ClientEvent) {
        when (event) {
            is ClientEvent.PostClient -> postClient(event.client)
            ClientEvent.GetClients -> getClients()
            is ClientEvent.FilterClients -> filterClients(event.name)
        }
    }

    private fun getClients() {
        viewModelScope.launch {
            clients = getClientsUseCase()
            updateState(ViewState(clients = clients))
        }
    }

    private fun filterClients(name: String) {
        if(name.isEmpty()){
            updateState(ViewState(clients = clients))
        }
        else{
            updateState(ViewState(clients = clients.filter { it.name.lowercase().contains(name.lowercase()) }))
        }
    }

    private fun postClient(client: Client) {
        if (client.name.isNotEmpty()) {
            viewModelScope.launch {
                postClientUseCase(client)
            }
        }
    }
}