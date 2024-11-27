package com.ahsan.service

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.serviceNavigation(navController: NavController) {
    composable(DestinationRoute.SERVICE_LIST_ROUTE) {
        ServiceListScreen(navController)
    }
    composable(DestinationRoute.SERVICE_CREATE_ROUTE) {
        ServiceCreateScreen(navController)
    }
    composable(DestinationRoute.SERVICE_SELECT_ROUTE) {
        SelectServicesScreen(navController)
    }
}