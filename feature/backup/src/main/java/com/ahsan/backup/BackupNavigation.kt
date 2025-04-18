package com.ahsan.backup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.BackupRoute

fun NavGraphBuilder.backupNavigation(navController: NavController) {
    composable<BackupRoute> {
        BackupScreen(navController)
    }
}