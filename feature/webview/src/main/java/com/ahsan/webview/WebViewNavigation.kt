package com.ahsan.webview

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ahsan.core.AppRoute.WebViewRoute

fun NavGraphBuilder.webViewNavigation(navController: NavController) {
    this.composable<WebViewRoute> {
        val webView = it.toRoute<WebViewRoute>()
        WebViewScreen(navController, webView.url)
    }
}