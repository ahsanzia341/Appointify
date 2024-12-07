package com.ahsan.service

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.R
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency

@Composable
fun ServiceCreateScreen(navController: NavController, id: Int = 0) {
    val viewModel = hiltViewModel<ServiceViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(true) {
        if(id != 0){
            viewModel.onTriggerEvent(ServiceEvent.FindById(id))
        }
    }
    ServiceCreateUI(viewState?.service, onSubmit = {
        viewModel.onTriggerEvent(ServiceEvent.PostService(it))
        navController.popBackStack()
    }){
        navController.popBackStack()
    }
}

@Composable
fun ServiceCreateUI(serviceAndCurrency: ServiceAndCurrency?, onSubmit: (Service) -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.create_service), onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val serviceObject = serviceAndCurrency?.service
            var service by remember {
                mutableStateOf(serviceObject ?: Service())
            }
            ThemeTextField(label = stringResource(id = R.string.name)) {
                service = service.copy(name = it)
            }
            ThemeTextField(label = stringResource(id = R.string.price), keyboardType = KeyboardType.Number, imeAction = ImeAction.Done) {
                service = service.copy(price = it.toDouble())
            }
            /*ThemeDropDown(label = stringResource(id = com.ahsan.composable.R.string.currency), currencies.map { "${it.symbol} ${it.name}" }) {
                currency = currencies[0].id
            }*/
            ThemeButton(text = stringResource(id = R.string.submit)) {
                onSubmit(service)
            }
        }
    }
}

@Composable
@Preview
fun ServiceCreatePreview(){
    ServiceCreateUI(null, onSubmit = {}){}
}