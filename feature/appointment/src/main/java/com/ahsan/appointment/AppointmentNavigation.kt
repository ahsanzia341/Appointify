package com.ahsan.appointment

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.AppointmentDetailRoute
import com.ahsan.core.AppRoute.CreateAppointmentRoute

fun NavGraphBuilder.appointmentNavigation(navController: NavController) {
    composable<CreateAppointmentRoute> {
        CreateAppointmentScreen(navController)
    }
    composable<AppointmentDetailRoute> {
        AppointmentDetailScreen(navController)
    }
}