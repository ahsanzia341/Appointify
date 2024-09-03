package com.ahsan.authentication

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.authenticationNavigation(navController: NavController) {
    composable(route = DestinationRoute.LOGIN_ROUTE) {
        LoginScreen(navController)
    }
    composable(route = DestinationRoute.REGISTER_ROUTE) {
        RegisterScreen(navController)
    }
    composable(route = DestinationRoute.FORGOT_PASSWORD_ROUTE) {
        ForgotPasswordScreen(navController)
    }
}