package com.ahsan.client

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Client
import com.ahsan.domain.client.DeleteClientUseCase
import com.ahsan.domain.client.FindClientByIdUseCase
import com.ahsan.domain.client.GetClientsUseCase
import com.ahsan.domain.client.PostClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val postClientUseCase: PostClientUseCase,
    private val getClientsUseCase: GetClientsUseCase,
    private val deleteClientUseCase: DeleteClientUseCase,
    private val findClientByIdUseCase: FindClientByIdUseCase): BaseViewModel<ViewState, ClientEvent>() {

    private var clients: List<Client> = listOf()

    override fun onTriggerEvent(event: ClientEvent) {
        when (event) {
            is ClientEvent.PostClient -> postClient(event.client)
            ClientEvent.GetClients -> getClients()
            is ClientEvent.FilterClients -> filterClients(event.name)
            is ClientEvent.DeleteClient -> delete(event.client)
            is ClientEvent.Validate -> validate(event.client)
            is ClientEvent.FindClientById -> findById(event.id)
        }
    }

    private fun getClients() {
        viewModelScope.launch {
            clients = getClientsUseCase()
            updateState(ViewState(clients = clients))
        }
    }

    private fun findById(id: Int){
        viewModelScope.launch {
            updateState(ViewState(client = findClientByIdUseCase(id)))
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

    private fun delete(client: Client){
        viewModelScope.launch {
            deleteClientUseCase(client)
            clients = clients.mapNotNull {
                if(it.id == client.id){
                    null
                }
                else{
                    it.copy()
                }
            }
            updateState(ViewState(clients = clients))
        }
    }

    private fun validate(client: Client): Boolean{
        if(client.name.isEmpty() || client.phoneNumber.isEmpty()){
            updateState(ViewState(validate = false, clients = listOf()))
            return false
        }
        updateState(ViewState(validate = true, clients = listOf()))
        return true
    }

    private fun postClient(client: Client) {
        if (validate(client)) {
            viewModelScope.launch {
                postClientUseCase(client)
            }
        }
    }
}