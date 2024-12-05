package com.ahsan.changepassword

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.changePasswordNavigation(navController: NavController) {
    composable(route = DestinationRoute.CHANGE_PASSWORD_ROUTE) {
        ChangePasswordScreen(navController)
    }
}