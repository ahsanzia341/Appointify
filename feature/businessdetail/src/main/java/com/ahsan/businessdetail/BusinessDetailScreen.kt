package com.ahsan.businessdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.R
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.core.AppRoute
import com.ahsan.data.models.Business

@Composable
fun CreateBusinessScreen(navController: NavController) {
    val viewModel = hiltViewModel<BusinessDetailViewModel>()
    BusinessDetailUI() {
        navController.popBackStack()
    }
}

@Composable
fun BusinessDetailUI(onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.business_detail), onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(modifier = Modifier.padding(padding)) {  }
    }
}

@Composable
@Preview
fun Preview(){
    SmartAppointmentTheme {
        BusinessDetailUI({})
    }
}