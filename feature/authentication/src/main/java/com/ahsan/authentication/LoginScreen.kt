package com.ahsan.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.ahsan.core.DestinationRoute

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    if(viewState?.loginState == true){
        navController.navigate(route = DestinationRoute.HOME_ROUTE, navOptions = NavOptions.Builder().setPopUpTo(DestinationRoute.HOME_ROUTE, false).build(),
            navigatorExtras = null)
    }
    RegisterUI(viewState?.error ?: "", viewState?.isLoading == true, onLoginPress = { email, password ->
        viewModel.onTriggerEvent(AuthEvent.Login(email, password))
    }) {
        navController.popBackStack()
    }
}

@Composable
@Preview
fun LoginPreview(){
    RegisterUI("", false, onLoginPress = { e, p ->

    }){

    }
}