package com.ahsan.setting

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ConfirmationDialog
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.core.Constant
import com.ahsan.core.DestinationRoute
import com.ahsan.core.extension.toEasyFormat
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Date

@Composable
fun SettingScreen(navController: NavController) {
    val viewModel = hiltViewModel<SettingViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(SettingEvent.IsLoggedIn)
    }

    SettingUI(viewState?.isLoading ?: true, viewState?.email, viewState?.lastBackupDate, onBackupPress = {
        viewModel.onTriggerEvent(SettingEvent.BackupData)
    }, onLogoutPress = {
        viewModel.onTriggerEvent(SettingEvent.Logout)
    }, onPrivacyPolicyPressed = {
        navController.navigate(DestinationRoute.WEB_VIEW_ROUTE.replace("{${DestinationRoute.PassedKey.URL}}", it))
    }, onAutoBackupSwitched = {
        if(it) viewModel.onTriggerEvent(SettingEvent.ScheduleBackup) else viewModel.onTriggerEvent(SettingEvent.CancelScheduleBackup)
    }, syncWithServer = {
        viewModel.onTriggerEvent(SettingEvent.LoadBackup)
    }, onDeletePress = {
        viewModel.onTriggerEvent(SettingEvent.DeleteAccount)
    }, onCurrencyPress = {
        navController.navigate(DestinationRoute.CURRENCY_ROUTE)
    }){
        navController.navigate(DestinationRoute.LOGIN_ROUTE)
    }
}

@Composable
fun SettingUI(isLoading: Boolean, email: String?, lastBackup: Date?, onBackupPress:() -> Unit, onLogoutPress:() -> Unit,
              onDeletePress: () -> Unit, onPrivacyPolicyPressed: (String) -> Unit, onAutoBackupSwitched: (Boolean) -> Unit,
              syncWithServer: () -> Unit, onCurrencyPress: () -> Unit, onLoginPress: () -> Unit) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(Constant.SHARED_PREF_KEY, Context.MODE_PRIVATE)
    var showConfirmation by remember {
        mutableStateOf(false)
    }

    var showDeleteAccountConfirmation by remember {
        mutableStateOf(false)
    }
    var backupState by remember {
        mutableStateOf(sharedPref.getBoolean(Constant.IS_AUTO_BACKUP, false))
    }

    Scaffold(topBar = {
        TopBar(title = stringResource(id = com.ahsan.composable.R.string.settings), navIcon = null)
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                if (email == null) {
                    SettingRowUI(
                        text = stringResource(id = com.ahsan.composable.R.string.login),
                        true
                    ) {
                        onLoginPress()
                    }
                } else {
                    ThemeText(text = email)
                    ThemeText(text = "Last backup: ${lastBackup?.toEasyFormat()}")
                    SettingRowUI(text = "Backup data") {
                        onBackupPress()
                    }
                    ThemeText(text = "Automatic daily backup.")
                    Switch(checked = backupState, onCheckedChange = {
                        backupState = it
                        sharedPref.edit().putBoolean(Constant.IS_AUTO_BACKUP, it).apply()
                        onAutoBackupSwitched(backupState)
                    })
                    SettingRowUI(text = "Synchronize with server") {
                        syncWithServer()
                    }
                }

                SettingRowUI(text = stringResource(id = com.ahsan.composable.R.string.currency), isNextPage = true){
                    onCurrencyPress()
                }
                SettingRowUI(
                    text = stringResource(id = com.ahsan.composable.R.string.privacy_policy),
                    true
                ) {
                    val encodedUrl = URLEncoder.encode(
                        "https://www.termsfeed.com/live/2b1724ab-a3a7-4bfa-b4aa-0573ee4abcf3",
                        StandardCharsets.UTF_8.toString()
                    )
                    onPrivacyPolicyPressed(encodedUrl)
                }
                SettingRowUI(
                    text = stringResource(id = com.ahsan.composable.R.string.feedback),
                    true
                ) {
                    if (email == null) {
                        onLoginPress()
                    }
                }

                if (email != null) {
                    SettingRowUI(text = stringResource(id = com.ahsan.composable.R.string.delete_account)) {
                        showDeleteAccountConfirmation = true
                    }
                    SettingRowUI(text = stringResource(id = com.ahsan.composable.R.string.logout)) {
                        showConfirmation = true
                    }

                }
                if (showConfirmation) {
                    ConfirmationDialog(
                        title = stringResource(id = com.ahsan.composable.R.string.confirmation),
                        text = "Your cloud backup will stop if you choose to logout but your data will persist.",
                        {
                            showConfirmation = false
                        }, {
                            onLogoutPress()
                            showConfirmation = false
                        })
                }
                if (showDeleteAccountConfirmation) {
                    ConfirmationDialog(
                        title = stringResource(id = com.ahsan.composable.R.string.delete_account),
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
    }
}

@Composable
@Preview
fun SettingPreview(){
    SettingUI(false, "null", Date(), {}, {}, {}, {}, {}, {}, {}){

    }
}