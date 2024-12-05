package com.ahsan.setting

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.ahsan.composable.R
import com.ahsan.composable.SettingRowUI
import com.ahsan.composable.SettingSwitchRowUI
import com.ahsan.composable.TopBar
import com.ahsan.core.Constant
import com.ahsan.core.DestinationRoute
import com.android.billingclient.api.ProductDetails
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Date

@Composable
fun SettingScreen(navController: NavController) {
    val viewModel = hiltViewModel<SettingViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    val activity = LocalContext.current as Activity
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(SettingEvent.IsLoggedIn)
    }

    SettingUI(viewState?.settings ?: listOf(), viewState?.isLoading ?: true, viewState?.email, viewState?.lastBackupDate, viewState?.billingProducts ?: listOf(),
        onBackupPress = {
        viewModel.onTriggerEvent(SettingEvent.BackupData)
    }, onLogoutPress = {
        viewModel.onTriggerEvent(SettingEvent.Logout)
    }, onPrivacyPolicyPressed = {
        navController.navigate(DestinationRoute.WEB_VIEW_ROUTE.replace("{${DestinationRoute.PassedKey.URL}}", it))
    }, onAutoBackupSwitched = {
        if(it) viewModel.onTriggerEvent(SettingEvent.ScheduleBackup) else viewModel.onTriggerEvent(SettingEvent.CancelScheduleBackup)
    }, onAccountDetailsPress = {
        navController.navigate(DestinationRoute.ACCOUNT_SETTING_ROUTE)
    }, onCurrencyPress = {
        navController.navigate(DestinationRoute.CURRENCY_ROUTE)
    }, onSubscribePress = {
        viewModel.onTriggerEvent(SettingEvent.LaunchBillingFlow(activity, it))
    }){
        navController.navigate(DestinationRoute.LOGIN_ROUTE)
    }
}

@Composable
fun SettingUI(settings: List<SettingRow>, isLoading: Boolean, email: String?, lastBackup: Date?, products: List<ProductDetails>, onBackupPress:() -> Unit, onLogoutPress:() -> Unit,
              onAccountDetailsPress: () -> Unit, onPrivacyPolicyPressed: (String) -> Unit, onAutoBackupSwitched: (Boolean) -> Unit,
              onCurrencyPress: () -> Unit, onSubscribePress: (ProductDetails) -> Unit, onLoginPress: () -> Unit) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(Constant.SHARED_PREF_KEY, Context.MODE_PRIVATE)
    var showConfirmation by remember {
        mutableStateOf(false)
    }
    val backupState by remember {
        mutableStateOf(sharedPref.getBoolean(Constant.IS_AUTO_BACKUP, false))
    }

    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.settings), navIcon = null)
    }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(
            Modifier
                .padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    settings.forEach {
                        if (it.loginRequired && email == null) {
                            return@forEach
                        }
                        else if(it.anonymousRow && email != null){
                            return@forEach
                        }
                        else if(!it.isSwitch){
                            SettingRowUI(text = stringResource(id = it.title), it.isNextPage) {
                                when (it.title) {
                                    R.string.login -> onLoginPress()
                                    R.string.account_settings -> onAccountDetailsPress()
                                    R.string.currency -> onCurrencyPress()
                                    R.string.privacy_policy -> onPrivacyPolicyPressed(URLEncoder.encode("https://www.termsfeed.com/live/2b1724ab-a3a7-4bfa-b4aa-0573ee4abcf3", StandardCharsets.UTF_8.toString()))
                                    R.string.feedback -> onLoginPress()
                                    R.string.logout -> onLogoutPress()
                                }
                            }
                        }
                        else{
                            SettingSwitchRowUI(text = stringResource(id = it.title), backupState) { checked ->
                                onAutoBackupSwitched(checked)
                                sharedPref.edit().putBoolean(Constant.IS_AUTO_BACKUP, checked).apply()
                            }
                        }
                    }
                }

                if (showConfirmation) {
                    ConfirmationDialog(
                        title = stringResource(id = R.string.confirmation),
                        text = "Your cloud backup will stop if you choose to logout but your data will persist.",
                        {
                            showConfirmation = false
                        }, {
                            onLogoutPress()
                            showConfirmation = false
                        })
                }
            }
        }
    }
}


@Composable
@Preview
fun SettingPreview(){
    MaterialTheme {
        SettingUI(settings,false, null, Date(), listOf(), {}, {}, {}, {}, {}, {}, {}){

        }
    }

}