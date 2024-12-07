package com.ahsan.accountsettings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.AccountSettingRoute

fun NavGraphBuilder.accountSettingsNavigation(navController: NavController) {
    composable<AccountSettingRoute> {
        AccountSettingsScreen(navController)
    }
}