package com.ahsan.client

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
import com.ahsan.composable.R
import com.ahsan.composable.ThemeFloatingActionButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute
import com.ahsan.data.models.Client

@Composable
fun ClientListScreen(navController: NavController) {
    val viewModel = hiltViewModel<ClientViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(ClientEvent.GetClients)
    }
    ClientListUI(viewState?.clients ?: listOf(), onFilterTextChanged = {
        viewModel.onTriggerEvent(ClientEvent.FilterClients(it))
    }, onItemClicked = {
    }){
        navController.navigate(DestinationRoute.CREATE_CLIENT_ROUTE)
    }
}

@Composable
fun ClientListUI(list: List<Client>, onFilterTextChanged: (String) -> Unit, onItemClicked: (Client) -> Unit, onAddClicked: () -> Unit) {
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.clients), navIcon = null)
    }, bottomBar = {
        Column(Modifier.fillMaxWidth()) {
            ThemeFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                onAddClicked()
            }
        }

    }, modifier = Modifier.padding(8.dp)) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {
            var query by remember {
                mutableStateOf("")
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()) {
                ThemeTextField(label = stringResource(id = R.string.search)) {
                    query = it
                    onFilterTextChanged(query)
                }
                LazyColumn {
                    items(list) {
                        ClientRow(client = it) {
                            onItemClicked(it)
                        }
                    }
                }
            }

            if (list.isEmpty())
                ThemeText(
                    text = stringResource(id = R.string.no_clients),
                    modifier = Modifier.align(Alignment.Center)
                )
        }
    }
}

@Composable
fun ClientRow(client: Client, onItemClicked: () -> Unit){
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.clickable {
            onItemClicked()
        }.padding(8.dp)) {
            ThemeText(text = client.name)
            ThemeText(text = client.phoneNumber)
        }
    }

}

@Composable
@Preview
fun ClientListPreview(){
    ClientListUI(listOf(), onFilterTextChanged = {

    }, onItemClicked = {

    }){}
}