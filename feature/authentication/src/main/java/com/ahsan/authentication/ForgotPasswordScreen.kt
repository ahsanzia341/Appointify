package com.ahsan.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.ahsan.composable.EmailTextField
import com.ahsan.composable.ErrorText
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.TopBar

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    ForgotPasswordUI(viewState?.error ?: "", viewState?.isLoading == true, onSubmit = {
        viewModel.onTriggerEvent(AuthEvent.ForgotPassword(it))
    }){
        navController.popBackStack()
    }
}

@Composable
fun ForgotPasswordUI(error: String, isLoading: Boolean, onSubmit: (String) -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.forgot_password), onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        var email by remember {
            mutableStateOf("")
        }
        Column(Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            EmailTextField {
                email = it
            }
            ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.submit), enabled = !isLoading) {
                onSubmit(email)
            }
            ErrorText(text = error)
        }
    }
}

@Composable
@Preview
fun ForgotPasswordPreview(){
    ForgotPasswordUI("Error", false, onSubmit = {}){}
}