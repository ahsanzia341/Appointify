package com.ahsan.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute

@Composable
fun SettingScreen(navController: NavController) {
    val viewModel = hiltViewModel<SettingViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    SettingUI(viewState?.isLoggedIn == true){
        navController.navigate(DestinationRoute.LOGIN_ROUTE)
    }
}

@Composable
fun SettingUI(isLoggedIn: Boolean, onLoginPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.settings), navIcon = null)
    }, modifier = Modifier.padding(8.dp)) {
        Column(Modifier.padding(it)) {
            if(!isLoggedIn){
                ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.login)) {
                    onLoginPress()
                }
            }
            else {
                ThemeButton(text = "Backup data") {

                }
            }
            ThemeButton(text = "Privacy policy") {

            }
            ThemeButton(text = "Terms and conditions") {

            }
            ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.feedback)) {

            }
            if(isLoggedIn){
                ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.logout)) {

                }
            }
        }
    }
}

@Composable
@Preview
fun SettingPreview(){
    SettingUI(false){

    }
}