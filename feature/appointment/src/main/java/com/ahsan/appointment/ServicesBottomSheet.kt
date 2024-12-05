package com.ahsan.appointment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeText
import com.ahsan.data.models.Currency
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency

@Composable
fun ServicesBottomSheet(services: List<ServiceAndCurrency>, onConfirmed: (List<ServiceAndCurrency>) -> Unit) {
    val selectedServices = remember {
        mutableListOf<ServiceAndCurrency>()
    }
    Scaffold(modifier = Modifier.padding(8.dp), bottomBar = {
        ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.confirm), modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()) {
            onConfirmed(selectedServices)
        }
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(services){
                ServiceRow(it){
                    if(selectedServices.contains(it)){
                        selectedServices.remove(it)
                    }
                    else{
                        selectedServices.add(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceRow(serviceAndCurrency: ServiceAndCurrency, onItemClicked: () -> Unit){
    val service = serviceAndCurrency.service
    val currency = serviceAndCurrency.currency
    var isSelected by remember {
        mutableStateOf(false)
    }
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable {
            isSelected = !isSelected
            onItemClicked()
        }) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)){
            Column(modifier = Modifier
                .padding(8.dp)) {
                ThemeText(text = service.name)
                ThemeText(text = "${currency.symbol}${service.price}")
            }
            if(isSelected){
                Icon(imageVector = Icons.Default.Check, contentDescription = "Check", modifier = Modifier.align(Alignment.CenterEnd))
            }
        }
    }
}

@Preview
@Composable
fun Preview(){
    ServicesBottomSheet(services = listOf(ServiceAndCurrency(Service(0, "My service", 10.0, 1), Currency(0, "Dollar", "$")))) {}
}