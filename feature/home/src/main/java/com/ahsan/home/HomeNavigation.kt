package com.ahsan.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute.HomeRoute
import com.ahsan.core.AppRoute.WelcomeRoute

fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable<HomeRoute> {
        HomeScreen(navController)
    }
    composable<WelcomeRoute>{
        WelcomeScreen(navController = navController)
    }
}