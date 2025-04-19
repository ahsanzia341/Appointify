package com.ahsan.changepassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.PasswordTextField
import com.ahsan.composable.TopBar
import com.ahsan.composable.R
import com.ahsan.composable.ThemeButton

@Composable
fun ChangePasswordScreen(navController: NavController) {
    val viewModel = hiltViewModel<ChangePasswordViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    ChangePasswordUI(viewState?.isLoading ?: false, onSubmitPress = {
        viewModel.onTriggerEvent(ChangePasswordEvent.SubmitNewPassword(it))
        navController.popBackStack()
    }) {
        navController.popBackStack()
    }
}

@Composable
fun ChangePasswordUI(isLoading: Boolean, onSubmitPress: (String) -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = { TopBar(title = stringResource(id = R.string.change_password), onClickNavIcon = { onBackPress() }) }, modifier = Modifier.padding(8.dp)) { padding ->
        if(isLoading){
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        else{
            Column(Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                var password by remember {
                    mutableStateOf("")
                }
                PasswordTextField( "New password") {
                    password = it
                }
                ThemeButton(text = stringResource(id = R.string.submit)) {
                    if(password.length > 5){
                        onSubmitPress(password)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun Preview(){
    ChangePasswordUI(false, onSubmitPress = {

    }){}
}