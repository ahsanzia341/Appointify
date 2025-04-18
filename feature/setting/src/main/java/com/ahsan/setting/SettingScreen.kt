package com.ahsan.setting

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
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
import com.ahsan.core.AppRoute.AccountSettingRoute
import com.ahsan.core.Constant
import com.ahsan.core.AppRoute.CreateBusinessRoute
import com.ahsan.core.AppRoute.CurrencyRoute
import com.ahsan.core.AppRoute.LoginRoute
import com.ahsan.core.AppRoute.WebViewRoute
import com.android.billingclient.api.ProductDetails
import java.util.Date
import androidx.core.content.edit

@Composable
fun SettingScreen(navController: NavController) {
    val viewModel = hiltViewModel<SettingViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    val activity = LocalActivity.current
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(SettingEvent.IsLoggedIn)
    }

    SettingUI(viewState?.settings ?: listOf(), viewState?.isLoading ?: true, viewState?.email, viewState?.lastBackupDate, viewState?.billingProducts ?: listOf(),
        onBackupPress = {
        viewModel.onTriggerEvent(SettingEvent.BackupData)
    }, onLogoutPress = {
        viewModel.onTriggerEvent(SettingEvent.Logout)
    }, onPrivacyPolicyPressed = {
        navController.navigate(WebViewRoute(it))
    }, onAutoBackupSwitched = {
        if(it) viewModel.onTriggerEvent(SettingEvent.ScheduleBackup) else viewModel.onTriggerEvent(SettingEvent.CancelScheduleBackup)
    }, onAccountDetailsPress = {
        navController.navigate(AccountSettingRoute)
    }, onCurrencyPress = {
        navController.navigate(CurrencyRoute)
    }, onSubscribePress = {
        if(activity != null)
        viewModel.onTriggerEvent(SettingEvent.LaunchBillingFlow(activity, it))
    }, onBusinessPress = {
        navController.navigate(CreateBusinessRoute)
        }){
        navController.navigate(LoginRoute)
    }
}

@Composable
fun SettingUI(settings: List<SettingRow>, isLoading: Boolean, email: String?, lastBackup: Date?, products: List<ProductDetails>, onBackupPress: () -> Unit, onLogoutPress:() -> Unit,
              onAccountDetailsPress: () -> Unit, onPrivacyPolicyPressed: (String) -> Unit, onAutoBackupSwitched: (Boolean) -> Unit,
              onCurrencyPress: () -> Unit, onBusinessPress:() -> Unit, onSubscribePress: (ProductDetails) -> Unit, onLoginPress: () -> Unit) {
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
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            return@Scaffold
        }
        Column(
            Modifier
                .padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                settings.forEach {
                    if (it.loginRequired && email == null) {
                        return@forEach
                    } else if (it.anonymousRow && email != null) {
                        return@forEach
                    } else if (!it.isSwitch) {
                        SettingRowUI(text = stringResource(id = it.title), it.isNextPage) {
                            when (it.title) {
                                R.string.login -> onLoginPress()
                                R.string.business -> onBusinessPress()
                                R.string.account_settings -> onAccountDetailsPress()
                                R.string.go_pro -> onSubscribePress(products[0])
                                R.string.backup_data -> onBackupPress()
                                R.string.currency -> onCurrencyPress()
                                R.string.privacy_policy -> onPrivacyPolicyPressed(
                                    "https://www.termsfeed.com/live/2b1724ab-a3a7-4bfa-b4aa-0573ee4abcf3"
                                )
                                R.string.feedback -> onLoginPress()
                                R.string.logout -> onLogoutPress()
                            }
                        }
                    } else {
                        SettingSwitchRowUI(
                            text = stringResource(id = it.title),
                            backupState
                        ) { checked ->
                            onAutoBackupSwitched(checked)
                            sharedPref.edit { putBoolean(Constant.IS_AUTO_BACKUP, checked) }
                        }
                    }
                }
            }

            if (showConfirmation) {
                ConfirmationDialog(
                    title = stringResource(id = R.string.confirmation),
                    text = stringResource(id = R.string.your_cloud_backup_will_stop_if_you_choose_to_logout_but_your_data_will_persist),
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


@Composable
@Preview
fun SettingPreview(){
    MaterialTheme {
        SettingUI(settings,false, null, Date(), listOf(), {}, {}, {}, {}, {}, {}, {}, {}){

        }
    }

}