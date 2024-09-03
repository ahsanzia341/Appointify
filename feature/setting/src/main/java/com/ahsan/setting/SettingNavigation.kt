package com.ahsan.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.settingNavigation(navController: NavController) {
    composable(DestinationRoute.SETTINGS_ROUTE) {
        SettingScreen(navController)
    }
}