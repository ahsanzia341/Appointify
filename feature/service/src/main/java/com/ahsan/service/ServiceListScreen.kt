package com.ahsan.service

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar

@Composable
fun ServiceListScreen(navController: NavController) {
    ServiceListUI()
}

@Composable
fun ServiceListUI(){
    Scaffold(topBar = {
        TopBar(title = "Services", navIcon = null)
    }) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            ThemeText(text = stringResource(id = com.ahsan.composable.R.string.no_services),
                modifier = Modifier.align(Alignment.Center))
        }
    }

}

@Composable
@Preview
fun ServiceListPreview(){
    ServiceListUI()
}