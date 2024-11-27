package com.ahsan.currency

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.currencyNavigation(navController: NavController) {
    composable(route = DestinationRoute.CURRENCY_ROUTE) {
        CurrencySettingScreen(navController)
    }
}