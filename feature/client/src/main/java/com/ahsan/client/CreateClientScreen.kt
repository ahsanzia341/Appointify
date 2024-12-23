package com.ahsan.client

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
fun CreateClientScreen(navController: NavController, id: Int = 0) {
    val viewModel = hiltViewModel<ClientViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        if (id > 0)
            viewModel.onTriggerEvent(ClientEvent.FindClientById(id))
    }
    LaunchedEffect(key1 = viewState?.validate) {
        if (viewState?.validate != null && viewState?.validate == true) {
            navController.popBackStack()
        }
    }
    CreateClientUI(viewState?.client, onSubmit = {
        viewModel.onTriggerEvent(ClientEvent.PostClient(it))
    }) {
        navController.popBackStack()
    }
}

@Composable
fun CreateClientUI(clientObject: Client?, onSubmit: (Client) -> Unit, onBackPress: () -> Unit){
    var client by remember {
        mutableStateOf(clientObject ?: Client())
    }
    LaunchedEffect(key1 = clientObject) {
        if(clientObject != null)
            client = clientObject
    }
    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.create_client), onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(Modifier.padding(padding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeTextField(label = stringResource(id = com.ahsan.composable.R.string.name), value = client.name, errorMessage = if(client.name.isEmpty()) stringResource(
                id = com.ahsan.composable.R.string.field_required, stringResource(id = com.ahsan.composable.R.string.name)) else "") {
                client = client.copy(name = it)
            }
            ThemeTextField(label = stringResource(id = com.ahsan.composable.R.string.phone_number), value = client.phoneNumber, keyboardType = KeyboardType.Phone, errorMessage = if(client.phoneNumber.isEmpty()) stringResource(
                id = com.ahsan.composable.R.string.field_required, stringResource(id = com.ahsan.composable.R.string.phone_number)) else "", imeAction = ImeAction.Done) {
                client = client.copy(phoneNumber = it)
            }
            ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.submit)) {
                onSubmit(client)
            }
        }
    }
}

@Composable
@Preview
fun CreateClientPreview(){
    CreateClientUI(null, onSubmit = {

    }){}
}