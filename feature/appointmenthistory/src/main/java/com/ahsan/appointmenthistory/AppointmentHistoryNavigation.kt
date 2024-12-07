package com.ahsan.appointmenthistory

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.AppointmentHistoryRoute

fun NavGraphBuilder.appointmentHistoryNavigation(navController: NavController) {
    composable<AppointmentHistoryRoute> {
        AppointmentHistoryScreen(navController)
    }
}