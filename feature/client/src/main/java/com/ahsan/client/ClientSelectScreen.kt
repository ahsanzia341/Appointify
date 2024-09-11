package com.ahsan.client

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.core.DestinationRoute

@Composable
fun ClientSelectScreen(navController: NavController) {
    val viewModel = hiltViewModel<ClientViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(ClientEvent.GetClients)
    }
    ClientListUI(list = viewState?.clients ?: listOf(), onFilterTextChanged = {}, onItemClicked = {
        navController.previousBackStackEntry?.savedStateHandle?.set(DestinationRoute.PassedKey.CLIENT_ID, it.id)
        navController.popBackStack()
    }) {
        navController.navigate(DestinationRoute.CREATE_CLIENT_ROUTE)
    }
}