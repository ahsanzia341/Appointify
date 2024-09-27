package com.ahsan.setting

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ConfirmationDialog
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.core.Constant
import com.ahsan.core.DestinationRoute

@Composable
fun SettingScreen(navController: NavController) {
    val viewModel = hiltViewModel<SettingViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    viewModel.onTriggerEvent(SettingEvent.IsLoggedIn)
    SettingUI(viewState?.email, onBackupPress = {
        viewModel.onTriggerEvent(SettingEvent.BackupData)
    }, onLogoutPress = {
        viewModel.onTriggerEvent(SettingEvent.Logout)
    }){
        navController.navigate(DestinationRoute.LOGIN_ROUTE)
    }
}

@Composable
fun SettingUI(email: String?, onBackupPress:() -> Unit, onLogoutPress:() -> Unit, onLoginPress: () -> Unit) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(Constant.SHARED_PREF_KEY, Context.MODE_PRIVATE)
    var showConfirmation by remember {
        mutableStateOf(false)
    }
    var backupState by remember {
        mutableStateOf(sharedPref.getBoolean(Constant.IS_AUTO_BACKUP, false))
    }

    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.settings), navIcon = null)
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (email == null) {
                ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.login)) {
                    onLoginPress()
                }
            } else {
                ThemeText(text = email)
                ThemeText(text = "Last backup: ")
                ThemeButton(text = "Backup data") {
                    onBackupPress()
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ThemeText(text = "Turn on automatic backup. Every night 11pm")
                    Switch(checked = backupState, onCheckedChange = {
                        backupState = it
                        sharedPref.edit().putBoolean(Constant.IS_AUTO_BACKUP, it).apply()
                    })
                }
            }



            ThemeButton(text = "Privacy policy") {

            }
            ThemeButton(text = "Terms and conditions") {

            }
            ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.feedback)) {

            }
            if (email != null) {
                ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.logout)) {
                    showConfirmation = true
                }
            }
            if (showConfirmation) {
                ConfirmationDialog(
                    title = "Confirmation",
                    text = "Your cloud backup will stop if you choose to logout. Would you like to continue?",
                    {
                        onLogoutPress()
                        showConfirmation = false
                    }, {
                        showConfirmation = false
                    }) {
                    showConfirmation = false
                }
            }
        }
    }
}

@Composable
@Preview
fun SettingPreview(){
    SettingUI("null", {}, {}){

    }
}