package com.ahsan.accountsettings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.accountSettingsNavigation(navController: NavController) {
    composable(route = DestinationRoute.ACCOUNT_SETTING_ROUTE) {
        AccountSettingsScreen(navController)
    }
}