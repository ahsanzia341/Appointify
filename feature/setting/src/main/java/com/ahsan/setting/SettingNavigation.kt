package com.ahsan.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.SettingsRoute

fun NavGraphBuilder.settingNavigation(navController: NavController) {
    composable<SettingsRoute> {
        SettingScreen(navController)
    }
}