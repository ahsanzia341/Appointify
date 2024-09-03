package com.ahsan.service

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun ServiceDetailScreen(navController: NavController) {
    ServiceListUI()
}

@Composable
fun ServiceDetailUI(){

}

@Composable
@Preview
fun ServiceDetailPreview(){
    ServiceDetailUI()
}