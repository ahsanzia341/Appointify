package com.ahsan.feedback

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.AppRoute

fun NavGraphBuilder.feedbackNavigation(navController: NavController) {
    composable<AppRoute.FeedbackRoute> {
        FeedbackScreen(navController)
    }
}