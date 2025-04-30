package com.ahsan.businessdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.R
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeHeaderText
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.core.AppRoute
import com.ahsan.data.models.Business

@Composable
fun BusinessDetailScreen(navController: NavController) {
    val viewModel = hiltViewModel<BusinessDetailViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    BusinessDetailUI(viewState?.business, onEditPress = {
        navController.navigate(AppRoute.CreateBusinessRoute(true))
    }) {
        navController.popBackStack()
    }
}

@Composable
fun BusinessDetailUI(business: Business?, onEditPress: () -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.business_detail), onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if(business == null){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            else{
                Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    ThemeHeaderText(Modifier.align(Alignment.CenterHorizontally), text = business.name)
                    ThemeText(text = stringResource(R.string.address_details, business.address))
                    ThemeText(text = stringResource(R.string.email_details, business.email))
                    ThemeText(text = stringResource(R.string.phone_details, business.phoneNumber))
                    ThemeText(text = stringResource(R.string.website_details, business.website))
                    ThemeButton(text = "Edit Business") {
                        onEditPress()
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun Preview(){
    SmartAppointmentTheme {
        BusinessDetailUI(null,{}, {})
    }
}