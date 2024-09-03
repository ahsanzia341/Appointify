package com.ahsan.appointmenthistory

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.appointmentHistoryNavigation(navController: NavController) {
    composable(route = DestinationRoute.APPOINTMENT_HISTORY_ROUTE) {
        AppointmentHistoryScreen()
    }
}