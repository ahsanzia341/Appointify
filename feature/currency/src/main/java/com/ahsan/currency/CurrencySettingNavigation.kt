package com.ahsan.currency

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.CurrencyRoute

fun NavGraphBuilder.currencyNavigation(navController: NavController) {
    composable<CurrencyRoute> {
        CurrencySettingScreen(navController)
    }
}