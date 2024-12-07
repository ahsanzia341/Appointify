package com.ahsan.service

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.ServiceCreateRoute
import com.ahsan.core.AppRoute.ServiceListRoute

fun NavGraphBuilder.serviceNavigation(navController: NavController) {
    composable<ServiceListRoute> {
        ServiceListScreen(navController)
    }
    composable<ServiceCreateRoute> {
        ServiceCreateScreen(navController, 0)
    }
}