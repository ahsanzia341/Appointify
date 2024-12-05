package com.ahsan.currency

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.data.models.Currency

@Composable
fun CurrencySettingScreen(navController: NavController) {
    val viewModel = hiltViewModel<CurrencySettingViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    if(viewState?.defaultCurrency != null){
        CurrencySettingUI(viewState?.currencies ?: listOf(), viewState?.defaultCurrency ?: 1, onSelect = {
            viewModel.onTriggerEvent(CurrencySettingEvent.SelectCurrency(it))
            navController.popBackStack()
        }) {
            navController.popBackStack()
        }
    }

}

@Composable
fun CurrencySettingUI(currencies: List<Currency>, defaultCurrencyId: Int, onSelect: (Int) -> Unit, onBackPress: () -> Unit){
    var selectedCurrencyId by remember {
        mutableIntStateOf(defaultCurrencyId)
    }
    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.currency), actions = {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }, onClickNavIcon = {
            onBackPress()
        })
    }, bottomBar = {
        ThemeButton(text = "Select as default currency") {
            onSelect(selectedCurrencyId)
        }
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn {
                items(currencies){
                    RowCurrency(currency = it, selectedCurrencyId) {
                        selectedCurrencyId = it.id
                    }
                }
            }
        }
    }
}

@Composable
fun RowCurrency(currency: Currency, selectedCurrencyId: Int, onSelect: () -> Unit){
    Box(modifier = Modifier
        .clickable {
            onSelect()
        }
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeText(text = "Name: ${currency.name}")
            ThemeText(text = "Symbol: ${currency.symbol}")
            HorizontalDivider(Modifier.fillMaxWidth(), color = Color.Black)
        }
        if(selectedCurrencyId == currency.id)
            Icon(imageVector = Icons.Filled.Check, contentDescription = "", modifier = Modifier.align(
                Alignment.CenterEnd))
    }
}