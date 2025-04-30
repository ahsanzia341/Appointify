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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
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
import com.ahsan.composable.ThemeCard
import com.ahsan.composable.ThemeFloatingActionButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.core.AppRoute.CreateClientRoute
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
        navController.navigate(CreateClientRoute)
    }, onDeleteClicked = {
        viewModel.onTriggerEvent(ClientEvent.DeleteClient(it))
    }){
        navController.navigate(CreateClientRoute)
    }
}

@Composable
fun ClientListUI(list: List<Client>, onFilterTextChanged: (String) -> Unit, onItemClicked: (Client) -> Unit,
                 onDeleteClicked: (Client) -> Unit, onAddClicked: () -> Unit) {
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.clients), navIcon = null)
    }, bottomBar = {
        Column(Modifier.fillMaxWidth()) {
            ThemeFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
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
            var isShowDeleteDialog by remember {
                mutableStateOf(Pair<Boolean, Client?>(false, null))
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()) {
                ThemeTextField(label = stringResource(id = R.string.search)) {
                    query = it
                    onFilterTextChanged(query)
                }
                LazyColumn {
                    items(list) {
                        ClientRow(client = it, onDeleteClicked = {
                            isShowDeleteDialog = Pair(true, it)
                        }) {
                            onItemClicked(it)
                        }
                    }
                }
            }
            if(isShowDeleteDialog.first){
                ConfirmationDialog(
                    title = stringResource(id = R.string.delete_confirmation),
                    text = stringResource(id = R.string.delete_confirmation_client_text),
                    onDismissRequest = { isShowDeleteDialog = Pair(false, null) }) {
                    if(isShowDeleteDialog.second != null)
                        onDeleteClicked(isShowDeleteDialog.second!!)
                    isShowDeleteDialog = Pair(false, null)
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
fun ClientRow(client: Client, onDeleteClicked: () -> Unit, onItemClicked: () -> Unit){
    ThemeCard (
        modifier = Modifier.fillMaxWidth().clickable {
            onItemClicked()
        }
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)){
            Column(modifier = Modifier
                .padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ThemeText(text = client.name)
                ThemeText(text = client.phoneNumber)
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
fun ClientListPreview(){
    SmartAppointmentTheme {
        ClientListUI(listOf(Client(0, "Test name", "12345")), onFilterTextChanged = {

        }, onItemClicked = {

        }, onDeleteClicked = {}){}
    }

}