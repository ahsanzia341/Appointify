package com.ahsan.webview

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ahsan.core.DestinationRoute

fun NavGraphBuilder.webViewNavigation(navController: NavController) {
    composable(DestinationRoute.WEB_VIEW_ROUTE) {
        WebViewScreen(navController, it.arguments?.getString(DestinationRoute.PassedKey.URL) ?: "")
    }
}