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
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ThemeFloatingActionButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute
import com.ahsan.data.models.Currency
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency

@Composable
fun ServiceListScreen(navController: NavController) {
    val viewModel = hiltViewModel<ServiceViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(ServiceEvent.GetServices)
    }
    ServiceListUI(viewState?.services ?: listOf(), onServiceClick = {
        navController.navigate(DestinationRoute.SERVICE_DETAIL_ROUTE)
    }, onAddClick = {
        navController.navigate(DestinationRoute.SERVICE_CREATE_ROUTE)
    })
}

@Composable
fun ServiceListUI(list: List<ServiceAndCurrency>, onServiceClick: (ServiceAndCurrency) -> Unit, onAddClick: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.services), navIcon = null)
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()){
            LazyColumn {
                items(list){
                    ServiceItem(service = it){
                        onServiceClick(it)
                    }
                }
            }
            if(list.isEmpty())
                ThemeText(text = stringResource(id = com.ahsan.composable.R.string.no_services),
                    modifier = Modifier.align(Alignment.Center))

            ThemeFloatingActionButton(modifier = Modifier
                .align(Alignment.BottomEnd)) {
                onAddClick()
            }
        }
    }
}

@Composable
fun ServiceItem(service: ServiceAndCurrency, onServiceClick: () -> Unit){
    Card(modifier = Modifier.padding(8.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onServiceClick()
            }.padding(8.dp)) {
            ThemeText(text = service.service.name)
            ThemeText(text = "${service.currency.symbol}${service.service.price}")
        }
    }

}

@Composable
@Preview
fun ServiceListPreview(){
    ServiceListUI(listOf(ServiceAndCurrency(Service(0, "Test", 10.0, 0), Currency(0, "", ""))), onServiceClick = {}, onAddClick = {})
}