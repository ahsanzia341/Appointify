package com.ahsan.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.ahsan.composable.PasswordTextField
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    if(viewState?.loginState == true){
        navController.navigate(route = DestinationRoute.HOME_ROUTE, navOptions = NavOptions.Builder().setPopUpTo(DestinationRoute.HOME_ROUTE, false).build(),
            navigatorExtras = null)
    }
    LoginUI(viewState?.error ?: "", viewState?.isLoading == true,
        onRegisterClick = {
            navController.navigate(DestinationRoute.REGISTER_ROUTE, NavOptions.Builder().setPopUpTo(DestinationRoute.REGISTER_ROUTE, true).build())
        }, onForgotPasswordClick = {
            navController.navigate(DestinationRoute.FORGOT_PASSWORD_ROUTE)
        },
        onLoginPress = { email, password ->
        viewModel.onTriggerEvent(AuthEvent.Login(email, password))
    }) {
        navController.popBackStack()
    }
}
@Composable
fun LoginUI(error: String, isLoading: Boolean, onForgotPasswordClick: () -> Unit, onRegisterClick: () -> Unit, onLoginPress: (String, String) -> Unit, onBackPress: () -> Unit) {
    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.login),
            onClickNavIcon = {
                onBackPress()
            })
    }) { padding ->
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ThemeTextField(
                keyboardType = KeyboardType.Email,
                label = stringResource(id = com.ahsan.composable.R.string.email)
            ) {
                email = it
            }
            PasswordTextField {
                password = it
            }
            ThemeButton(text = "Forgot Password?"){
                onForgotPasswordClick()
            }
            ThemeButton(enabled = !isLoading, text = stringResource(id = com.ahsan.composable.R.string.login)) {
                onLoginPress(email, password)
            }

            if(error.isNotEmpty())
                ThemeText(text = error, color = Color.Red)

            ThemeText(text = "Don't have an account?")
            ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.register)) {
                onRegisterClick()
            }
        }
    }
}
@Composable
@Preview
fun LoginPreview(){
    LoginUI("", false, onRegisterClick = {

    }, onForgotPasswordClick = {}, onLoginPress = { e, p ->

    }){

    }
}