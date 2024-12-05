package com.ahsan.client

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.clientNavigation(navController: NavController) {
    composable(route = DestinationRoute.CLIENT_LIST_ROUTE) {
        ClientListScreen(navController)
    }
    composable(route = DestinationRoute.CREATE_CLIENT_ROUTE) {
        CreateClientScreen(navController, it.arguments?.getString(DestinationRoute.PassedKey.ID)?.toIntOrNull() ?: 0)
    }
}