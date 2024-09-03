package com.ahsan.authentication

import android.content.res.Resources.Theme
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
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    if(viewState?.loginState == true){
        navController.navigate(route = DestinationRoute.HOME_ROUTE, navOptions = NavOptions.Builder().setPopUpTo(DestinationRoute.HOME_ROUTE, false).build(),
            navigatorExtras = null)
    }
    RegisterUI(viewState?.error ?: "", viewState?.isLoading == true, onLoginPress = { email, password ->
        viewModel.onTriggerEvent(AuthEvent.Register(email, password))
    }) {
        navController.popBackStack()
    }
}

@Composable
fun RegisterUI(error: String, isLoading: Boolean, onLoginPress: (String, String) -> Unit, onBackPress: () -> Unit) {
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
            ThemeTextField(
                keyboardType = KeyboardType.Email,
                label = stringResource(id = com.ahsan.composable.R.string.email),
                isError = email.isEmpty()
            ) {
                email = it
            }
            ThemeTextField(
                keyboardType = KeyboardType.Password,
                label = stringResource(id = com.ahsan.composable.R.string.password),
                isError = password.isEmpty() || password.length < 5
            ) {
                password = it
            }
            ThemeText(text = "Forgot Password?")
            ThemeButton(enabled = !isLoading, text = stringResource(id = com.ahsan.composable.R.string.create_account)) {
                onLoginPress(email, password)
            }
            ThemeText(text = error, color = Color.Red)
            
            ThemeText(text = "Already have an account? Sign In")
        }
    }
}

@Composable
@Preview
fun RegisterPreview(){
    RegisterUI("", false, onLoginPress = { e, p ->

    }){

    }
}