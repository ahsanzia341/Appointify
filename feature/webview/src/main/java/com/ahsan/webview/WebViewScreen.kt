package com.ahsan.webview

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.ahsan.composable.TopBar

@Composable
fun WebViewScreen(navController: NavController, url: String) {
    Scaffold(topBar = {
        TopBar(onClickNavIcon = {
            navController.navigateUp()
        }, title = stringResource(id = com.ahsan.composable.R.string.privacy_policy))
    }) {
        AndroidView(
            factory = { context ->
                WebView(context)
            },
            update = { webView ->
                webView.loadUrl(url)
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}