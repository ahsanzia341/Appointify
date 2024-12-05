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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ConfirmationDialog
import com.ahsan.composable.R
import com.ahsan.composable.ThemeFloatingActionButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute
import com.ahsan.core.DestinationRoute.PassedKey
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
        navController.navigate(DestinationRoute.SERVICE_CREATE_ROUTE.replace("{${PassedKey.ID}}", it))
    }, onAddClick = {
        navController.navigate(DestinationRoute.SERVICE_CREATE_ROUTE)
    }, onDeleteClicked = {
        viewModel.onTriggerEvent(ServiceEvent.Delete(it.service))
    }, onFilterTextChanged = {

    })
}

@Composable
fun ServiceListUI(list: List<ServiceAndCurrency>, onDeleteClicked: (ServiceAndCurrency) -> Unit, onFilterTextChanged: (String) -> Unit, onServiceClick: (String) -> Unit, onAddClick: () -> Unit) {
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.services), navIcon = null)
    }, bottomBar = {
        Column(Modifier.fillMaxWidth()) {
            ThemeFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            ) {
                onAddClick()
            }
        }
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            var query by remember {
                mutableStateOf("")
            }
            var isShowDeleteDialog by remember {
                mutableStateOf(Pair<Boolean, ServiceAndCurrency?>(false, null))
            }
            Column {
                ThemeTextField(label = stringResource(id = R.string.search)) {
                    query = it
                    onFilterTextChanged(query)
                }
                LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                    items(list) {
                        ServiceItem(service = it, onDeleteClicked = {
                            isShowDeleteDialog = Pair(true, it)
                        }) {
                            onServiceClick(it.service.serviceId.toString())
                        }
                    }
                }
            }
            if (list.isEmpty())
                ThemeText(
                    text = stringResource(id = R.string.no_services),
                    modifier = Modifier.align(Alignment.Center)
                )
            if(isShowDeleteDialog.first){
                ConfirmationDialog(
                    title = stringResource(id = R.string.delete_confirmation),
                    text = stringResource(id = R.string.delete_confirmation_service_text),
                    onDismissRequest = { isShowDeleteDialog = Pair(true, null) }) {
                    if(isShowDeleteDialog.second != null)
                        onDeleteClicked(isShowDeleteDialog.second!!)

                    isShowDeleteDialog = Pair(false, null)
                }
            }
        }

    }
}

@Composable
fun ServiceItem(service: ServiceAndCurrency, onDeleteClicked: () -> Unit, onServiceClick: () -> Unit){
    Card(modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onServiceClick()
                }
                .padding(8.dp)) {
                ThemeText(text = service.service.name)
                ThemeText(text = "${service.currency.symbol}${service.service.price}")
            }
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    onDeleteClicked()
                })
        }
    }

}

@Composable
@Preview
fun ServiceListPreview(){
    ServiceListUI(listOf(ServiceAndCurrency(Service(0, "Test", 10.0, 0), Currency(0, "", ""))), {}, {}, onServiceClick = {}, onAddClick = {})
}