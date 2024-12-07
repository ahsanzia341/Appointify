package com.ahsan.business

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.CreateBusinessRoute

fun NavGraphBuilder.createBusinessNavigation(navController: NavController) {
    composable<CreateBusinessRoute> {
        CreateBusinessScreen(navController)
    }
}