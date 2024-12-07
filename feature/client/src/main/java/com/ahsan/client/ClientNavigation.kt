package com.ahsan.client

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ahsan.core.AppRoute.ClientListRoute
import com.ahsan.core.AppRoute.CreateClientRoute

fun NavGraphBuilder.clientNavigation(navController: NavController) {
    composable<ClientListRoute> {
        ClientListScreen(navController)
    }
    composable<CreateClientRoute> {
        val route = it.toRoute<CreateClientRoute>()
        CreateClientScreen(navController)
    }
}