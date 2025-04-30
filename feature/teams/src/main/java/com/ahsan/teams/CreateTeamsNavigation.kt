package com.ahsan.teams

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute

fun NavGraphBuilder.createTeamsNavigation(navController: NavController) {
    composable<AppRoute.CreateTeamsRoute> {
        CreateTeamsScreen(navController)
    }
}