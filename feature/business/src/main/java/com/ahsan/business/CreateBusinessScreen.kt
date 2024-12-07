package com.ahsan.business

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.EmailTextField
import com.ahsan.composable.R
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeTextField
import com.ahsan.composable.TopBar
import com.ahsan.data.models.Business

@Composable
fun CreateBusinessScreen(navController: NavController) {
    val viewModel = hiltViewModel<CreateBusinessViewModel>()
    CreateBusinessUI({
        viewModel.onTriggerEvent(CreateBusinessEvent.CreateBusiness(it))
        navController.popBackStack()
    }) {
        navController.popBackStack()
    }
}

@Composable
fun CreateBusinessUI(onSubmit: (Business) -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.create_business), onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        var business by remember {
            mutableStateOf(Business())
        }
        Column(modifier = Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeTextField(label = stringResource(id = R.string.name)) {
                business = business.copy(name = it)
            }
            ThemeTextField(label = stringResource(id = R.string.phone_number), keyboardType = KeyboardType.Phone) {
                business = business.copy(phoneNumber = it)
            }
            EmailTextField {
                business = business.copy(email = it)
            }
            ThemeTextField(label = stringResource(id = R.string.address)) {
                business = business.copy(address = it)
            }
            ThemeTextField(label = stringResource(id = R.string.description)) {
                business = business.copy(description = it)
            }
            ThemeButton(text = stringResource(id = R.string.submit)) {
                onSubmit(business)
            }
        }
    }
}

@Composable
@Preview
fun Preview(){
    CreateBusinessUI({}){}
}