package com.ahsan.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val viewModel = hiltViewModel<AuthViewModel>()
    ForgotPasswordUI(onSubmit = {
        viewModel.onTriggerEvent(AuthEvent.ForgotPassword(it))
    }){
        navController.popBackStack()
    }
}

@Composable
fun ForgotPasswordUI(onSubmit: (String) -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = "Forgot Password", onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        var email by remember {
            mutableStateOf("")
        }
        Column(Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeTextField(label = stringResource(id = com.ahsan.composable.R.string.email)) {
                email = it
            }
            ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.submit)) {
                onSubmit(email)
            }
        }
    }
}

@Composable
@Preview
fun ForgotPasswordPreview(){
    ForgotPasswordUI(onSubmit = {}){}
}