package com.ahsan.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.ahsan.composable.EmailTextField
import com.ahsan.composable.ErrorText
import com.ahsan.composable.GoogleSignInButton
import com.ahsan.composable.PasswordTextField
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeHeaderText
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.core.AppRoute
import com.ahsan.core.AppRoute.RegisterRoute

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(viewState?.loginState) {
        if(viewState?.loginState == true){
            navController.navigate(
                AppRoute.HomeRoute,
                NavOptions.Builder().setPopUpTo(AppRoute.HomeRoute, true).build()
            )
        }
    }
    RegisterUI(
        viewState?.error ?: "",
        viewState?.isLoading == true,
        onLoginPress = { email, password ->
            viewModel.onTriggerEvent(AuthEvent.ValidateForRegister(context, email.trim(), password.trim()))
        },
        onSignInWithGooglePressed = {
            viewModel.onTriggerEvent(AuthEvent.SignInWithGoogle(context))
        }) {
        navController.popBackStack()
    }
}

@Composable
fun RegisterUI(error: String, isLoading: Boolean,
               onLoginPress: (String, String) -> Unit, onSignInWithGooglePressed: () -> Unit, onBackPress: () -> Unit) {
    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.register),
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
            EmailTextField {
                email = it
            }
            PasswordTextField(true) {
                password = it
            }
            ThemeButton(enabled = !isLoading, text = stringResource(id = com.ahsan.composable.R.string.create_account)) {
                onLoginPress(email, password)
            }
            ErrorText(text = error)
            ThemeHeaderText(text = stringResource(com.ahsan.composable.R.string.or))
            GoogleSignInButton {
                onSignInWithGooglePressed()
            }
        }
    }
}

@Composable
@Preview
fun RegisterPreview(){
    SmartAppointmentTheme {
        RegisterUI("", false, onLoginPress = { _, _ ->

        }, onSignInWithGooglePressed = {}){

        }
    }

}