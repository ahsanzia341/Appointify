package com.ahsan.client

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.data.models.Client

@Composable
fun CreateClientScreen(navController: NavController) {
    val viewModel = hiltViewModel<ClientViewModel>()
    CreateClientUI(onSubmit = {
        viewModel.onTriggerEvent(ClientEvent.PostClient(it))
        navController.popBackStack()
    }){
        navController.popBackStack()
    }
}

@Composable
fun CreateClientUI(onSubmit: (Client) -> Unit, onBackPress: () -> Unit){
    var name by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.create_client), onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(Modifier.padding(padding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeTextField(label = stringResource(id = com.ahsan.composable.R.string.name)) {
                name = it
            }
            ThemeTextField(label = stringResource(id = com.ahsan.composable.R.string.phone_number), keyboardType = KeyboardType.Phone) {
                phoneNumber = it
            }
            ThemeButton(text = "Submit") {
                onSubmit(Client(name = name, phoneNumber = phoneNumber))
            }
        }
    }

}

@Composable
@Preview
fun CreateClientPreview(){
    CreateClientUI(onSubmit = {

    }){}
}