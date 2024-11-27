package com.ahsan.service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.data.models.Service

@Composable
fun ServiceCreateScreen(navController: NavController) {
    val viewModel = hiltViewModel<ServiceViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    ServiceCreateUI(viewState?.defaultCurrency ?: 1, onSubmit = {
        viewModel.onTriggerEvent(ServiceEvent.PostService(it))
        navController.popBackStack()
    }){
        navController.popBackStack()
    }
}

@Composable
fun ServiceCreateUI(currencyId: Int, onSubmit: (Service) -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = "Create Service", onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            var name by remember {
                mutableStateOf("")
            }
            var price by remember {
                mutableDoubleStateOf(0.0)
            }
            ThemeTextField(label = stringResource(id = com.ahsan.composable.R.string.name)) {
                name = it
            }
            ThemeTextField(label = stringResource(id = com.ahsan.composable.R.string.price), keyboardType = KeyboardType.Number, imeAction = ImeAction.Done) {
                price = it.toDouble()
            }
            /*ThemeDropDown(label = stringResource(id = com.ahsan.composable.R.string.currency), currencies.map { "${it.symbol} ${it.name}" }) {
                currency = currencies[0].id
            }*/
            ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.submit)) {
                onSubmit(Service(0, name, price, currencyId))
            }
        }
    }
}

@Composable
@Preview
fun ServiceCreatePreview(){
    ServiceCreateUI(1, onSubmit = {}){}
}