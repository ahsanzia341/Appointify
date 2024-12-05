package com.ahsan.business

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.createBusinessNavigation(navController: NavController) {
    composable(DestinationRoute.CREATE_BUSINESS_ROUTE) {
        CreateBusinessScreen(navController)
    }
}