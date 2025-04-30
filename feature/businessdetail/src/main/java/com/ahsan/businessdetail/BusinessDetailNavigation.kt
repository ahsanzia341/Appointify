package com.ahsan.businessdetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute

fun NavGraphBuilder.businessDetailNavigation(navController: NavController) {
    composable<AppRoute.BusinessDetailRoute> {
        BusinessDetailScreen(navController)
    }
}