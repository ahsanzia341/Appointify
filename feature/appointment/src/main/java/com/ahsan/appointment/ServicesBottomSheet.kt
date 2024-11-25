package com.ahsan.appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahsan.composable.ThemeText
import com.ahsan.data.models.Currency
import com.ahsan.data.models.Service
import com.ahsan.data.models.ServiceAndCurrency

@Composable
fun ServicesBottomSheet(services: List<ServiceAndCurrency>) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(services){
            ServiceRow(it)
        }
    }
}

@Composable
fun ServiceRow(serviceAndCurrency: ServiceAndCurrency){
    val service = serviceAndCurrency.service
    val currency = serviceAndCurrency.currency
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ThemeText(text = service.name)
        ThemeText(text = "${currency.symbol}${service.price}")
    }
}

@Preview
@Composable
fun Preview(){
    ServicesBottomSheet(services = listOf(ServiceAndCurrency(Service(0, "My service", 10.0, 1), Currency(0, "Dollar", "$"))))
}