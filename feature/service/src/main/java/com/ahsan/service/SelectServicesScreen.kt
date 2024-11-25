package com.ahsan.service

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute
import com.ahsan.data.models.ServiceAndCurrency

@Composable
fun SelectServicesScreen(navController: NavController) {
    val viewModel = hiltViewModel<ServiceViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(ServiceEvent.GetServices)
    }
    SelectServicesUI(viewState?.services ?: listOf(), viewState?.selectedServices ?: listOf(),
        onSelect = {
            viewModel.onTriggerEvent(ServiceEvent.SelectService(it))
        }) {
        navController.previousBackStackEntry?.savedStateHandle?.set(DestinationRoute.PassedKey.SERVICE_IDS, viewState?.services?.map { it.service.id })
        navController.popBackStack()
    }
}

@Composable
fun SelectServicesUI(list: List<ServiceAndCurrency>, selectedList: List<ServiceAndCurrency>, onSelect: (ServiceAndCurrency) -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = "Select Services", onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()){
            LazyColumn {
                items(list){ service ->
                    RowService(serviceAndCurrency = service, selectedList.find { it.service.id == service.service.id } != null){
                        onSelect(service)
                    }
                }
            }
            if(selectedList.isNotEmpty()){
                ThemeButton(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(8.dp), text = "${selectedList.size} Selected") {
                    onBackPress()
                }
            }
        }
    }
}

@Composable
fun RowService(serviceAndCurrency: ServiceAndCurrency, isSelected: Boolean, onSelect: () -> Unit){
    val service = serviceAndCurrency.service
    val currency = serviceAndCurrency.currency
    Box(modifier = Modifier.clickable {
        onSelect()
    }.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeText(text = service.name)
            ThemeText(text = currency.symbol + service.price.toString())
        }
        if(isSelected)
            Icon(imageVector = Icons.Filled.Check, contentDescription = "", modifier = Modifier.align(Alignment.CenterEnd))
    }
}

@Composable
@Preview
fun SelectServicesPreview(){
    SelectServicesUI(listOf(), listOf(), onSelect = {

    }){

    }
}