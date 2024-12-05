package com.ahsan.accountsettings

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.compose.rememberNavController
import com.ahsan.composable.ConfirmationDialog
import com.ahsan.composable.R
import com.ahsan.composable.SettingRowUI
import com.ahsan.composable.TopBar
import com.ahsan.core.DestinationRoute

@Composable
fun AccountSettingsScreen(navController: NavController) {
    val viewModel = hiltViewModel<AccountSettingViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    AccountSettingUI(navController, viewState?.settings ?: listOf(), onDeletePress = {}){
        navController.popBackStack()
    }
}

@Composable
fun AccountSettingUI(navController: NavController, settings: List<SettingRow>, onDeletePress: () -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.account_settings), onClickNavIcon = {
        onBackPress()
    })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        var showDeleteAccountConfirmation by remember {
            mutableStateOf(false)
        }
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(settings){
                SettingRowUI(text = stringResource(id = it.title), it.isNextPage){
                    when (it.title) {
                        R.string.edit_account_details -> {
                            navController.navigate(DestinationRoute.EDIT_ACCOUNT_ROUTE)
                        }
                        R.string.change_password -> {
                            navController.navigate(DestinationRoute.CHANGE_PASSWORD_ROUTE)
                        }
                        else -> {
                            showDeleteAccountConfirmation = true
                        }
                    }
                }
            }
        }
        if (showDeleteAccountConfirmation) {
            ConfirmationDialog(
                title = stringResource(id = R.string.delete_account),
                text = "Deleting account will erase all your account data from the server but it will still persist on your local storage. Are you sure you would like to delete your account?",
                {
                    showDeleteAccountConfirmation = false
                }, {
                    onDeletePress()
                    showDeleteAccountConfirmation = false
                })
        }
    }
}

@Composable
@Preview
fun Preview(){
    AccountSettingUI(rememberNavController(), listOf(), {}){}
}