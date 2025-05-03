package com.ahsan.appointment

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahsan.composable.R
import com.ahsan.composable.ThemeCard
import com.ahsan.composable.ThemeText
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.data.models.Client

@Composable
fun ClientSelectScreen(clients: List<Client>, onSelected: (Client) -> Unit) {
    Box(modifier = Modifier
        .padding(8.dp)
        .fillMaxSize()) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()) {
            LazyColumn {
                items(clients) {
                    ClientRow(client = it) {
                        onSelected(it)
                    }
                }
            }
        }

        if (clients.isEmpty())
            ThemeText(
                text = stringResource(id = R.string.no_clients),
                modifier = Modifier.align(Alignment.Center)
            )
    }
}

@Composable
fun ClientRow(client: Client, onItemClicked: () -> Unit){
    ThemeCard(Modifier.clickable{
        onItemClicked()
    }) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)){
            Column(modifier = Modifier
                .padding(8.dp)) {
                ThemeText(text = client.name)
                ThemeText(text = client.phoneNumber)
            }
        }
    }
}

@Composable
@Preview
fun ClientSelectPreview(){
    SmartAppointmentTheme {
        ClientSelectScreen(clients = listOf(Client(0, "Test", "12345"))) { }
    }
}