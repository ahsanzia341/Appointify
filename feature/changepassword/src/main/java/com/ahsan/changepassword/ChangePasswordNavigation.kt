package com.ahsan.changepassword

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.ChangePasswordRoute

fun NavGraphBuilder.changePasswordNavigation(navController: NavController) {
    composable<ChangePasswordRoute> {
        ChangePasswordScreen(navController)
    }
}