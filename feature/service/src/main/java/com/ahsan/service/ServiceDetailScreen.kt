package com.ahsan.service

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.ahsan.composable.TopBar

@Composable
fun ServiceDetailScreen(navController: NavController) {
    ServiceDetailUI{
        navController.popBackStack()
    }
}

@Composable
fun ServiceDetailUI(onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = "Service Detail", onClickNavIcon = {
            onBackPress()
        })
    }) { padding ->
        Box(modifier = Modifier.padding(padding)){

        }
    }
}

@Composable
@Preview
fun ServiceDetailPreview(){
    ServiceDetailUI{}
}