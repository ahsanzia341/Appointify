package com.ahsan.authentication

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.ForgotPasswordRoute
import com.ahsan.core.AppRoute.LoginRoute
import com.ahsan.core.AppRoute.RegisterRoute

fun NavGraphBuilder.authenticationNavigation(navController: NavController) {
    composable<LoginRoute> {
        LoginScreen(navController)
    }
    composable<RegisterRoute> {
        RegisterScreen(navController)
    }
    composable<ForgotPasswordRoute> {
        ForgotPasswordScreen(navController)
    }
}