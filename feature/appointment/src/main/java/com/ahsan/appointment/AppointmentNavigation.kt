package com.ahsan.appointment

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.appointmentNavigation(navController: NavController) {
    composable(route = DestinationRoute.CREATE_APPOINTMENT_ROUTE) {
        CreateAppointmentScreen(navController)
    }
    composable(route = DestinationRoute.UPDATE_APPOINTMENT_ROUTE) {
        UpdateAppointmentScreen(navController, it.arguments?.getInt("id") ?: 0)
    }
    composable(route = DestinationRoute.APPOINTMENT_DETAIL_ROUTE) {
        AppointmentDetailScreen(navController, it.arguments?.getInt("id") ?: 0)
    }
}