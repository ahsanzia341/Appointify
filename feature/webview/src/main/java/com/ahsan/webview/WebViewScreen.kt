package com.ahsan.webview

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahsan.composable.TopBar

@Composable
fun WebViewScreen(navController: NavController, url: String) {
    Scaffold(topBar = {
        TopBar(onClickNavIcon = {
            navController.navigateUp()
        }, title = stringResource(id = com.ahsan.composable.R.string.privacy_policy))
    }) {
        var isLoading by remember { mutableStateOf(true) }
        Box(Modifier.fillMaxSize()) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = object : WebViewClient(){
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                isLoading = true
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false
                            }
                        }
                    }
                },
                update = { webView ->
                    webView.loadUrl(url)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
            if(isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
@Preview
fun Preview(){
    WebViewScreen(rememberNavController(), "https://google.com")
}