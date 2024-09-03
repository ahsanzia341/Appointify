package com.ahsan.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable(DestinationRoute.HOME_ROUTE) {
        HomeScreen(navController)
    }
    composable(DestinationRoute.WELCOME_ROUTE){
        WelcomeScreen(navController = navController)
    }
}