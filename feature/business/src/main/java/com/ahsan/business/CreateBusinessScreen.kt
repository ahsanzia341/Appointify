package com.ahsan.business

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.R
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.data.models.Business

@Composable
fun CreateBusinessScreen(navController: NavController) {
    val viewModel = hiltViewModel<CreateBusinessViewModel>()
    CreateBusinessUI({
        viewModel.onTriggerEvent(CreateBusinessEvent.CreateBusiness(Business()))
    }) {
        navController.popBackStack()
    }
}

@Composable
fun CreateBusinessUI(onSubmit: () -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = "Create Business", onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        var name by remember {
            mutableStateOf("")
        }
        var address by remember {
            mutableStateOf("")
        }
        Column(modifier = Modifier.padding(padding)) {
            ThemeTextField(label = stringResource(id = R.string.name)) {
                name = it
            }
            ThemeTextField(label = "Address") {
                address = it
            }
            ThemeButton(text = stringResource(id = R.string.submit)) {
                onSubmit()
            }
        }
    }
}

@Composable
@Preview
fun Preview(){
    CreateBusinessUI({}){}
}