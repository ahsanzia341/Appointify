package com.ahsan.client

import com.ahsan.data.models.Client

data class ViewState(
    val clients: List<Client>, val validate: Boolean? = null)

sealed class ClientEvent {
    data class PostClient(val client: Client): ClientEvent()
    data object GetClients: ClientEvent()
    data class FilterClients(val name: String): ClientEvent()
    data class DeleteClient(val client: Client): ClientEvent()
    data class Validate(val client: Client): ClientEvent()
}
