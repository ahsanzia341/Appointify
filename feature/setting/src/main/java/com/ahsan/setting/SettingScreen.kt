package com.ahsan.setting

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.ConfirmationDialog
import com.ahsan.composable.R
import com.ahsan.composable.SettingRowUI
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.core.AppRoute
import com.ahsan.core.AppRoute.AccountSettingRoute
import com.ahsan.core.AppRoute.CreateBusinessRoute
import com.ahsan.core.AppRoute.CurrencyRoute
import com.ahsan.core.AppRoute.LoginRoute
import com.ahsan.core.AppRoute.WebViewRoute
import com.android.billingclient.api.ProductDetails

@Composable
fun SettingScreen(navController: NavController) {
    val viewModel = hiltViewModel<SettingViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    val activity = LocalActivity.current
    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(SettingEvent.IsLoggedIn)
    }

    SettingUI(
        viewState?.settings ?: listOf(),
        viewState?.isLoading ?: true,
        viewState?.email,
        viewState?.billingProducts ?: listOf(),
        onLogoutPress = {
            viewModel.onTriggerEvent(SettingEvent.Logout)
        },
        onPrivacyPolicyPressed = {
            navController.navigate(WebViewRoute(it))
        },
        onAccountDetailsPress = {
            navController.navigate(AccountSettingRoute)
        },
        onCurrencyPress = {
            navController.navigate(CurrencyRoute)
        },
        onSubscribePress = {
            if (activity != null)
                viewModel.onTriggerEvent(SettingEvent.LaunchBillingFlow(activity, it))
        },
        onBusinessPress = {
            navController.navigate(CreateBusinessRoute)
        },
        onBackupPress = {
            if(viewState?.isBusinessPresent == true){
                navController.navigate(AppRoute.BackupRoute)
            }
            else{
                Toast.makeText(activity, activity?.getString(R.string.please_add_your_business_details_before_creating_backup), Toast.LENGTH_LONG).show()
            }
        }) {
        navController.navigate(LoginRoute)
    }
}

@Composable
fun SettingUI(settings: List<SettingRow>, isLoading: Boolean, email: String?, products: List<ProductDetails>, onLogoutPress:() -> Unit,
              onAccountDetailsPress: () -> Unit, onPrivacyPolicyPressed: (String) -> Unit, onBackupPress: () -> Unit,
              onCurrencyPress: () -> Unit, onBusinessPress:() -> Unit, onSubscribePress: (ProductDetails) -> Unit, onLoginPress: () -> Unit) {
    var showConfirmation by remember {
        mutableStateOf(false)
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
                                R.string.backup_data -> onBackupPress()
                                R.string.account_settings -> onAccountDetailsPress()
                                R.string.go_pro -> onSubscribePress(products[0])
                                R.string.currency -> onCurrencyPress()
                                R.string.privacy_policy -> onPrivacyPolicyPressed(
                                    "https://www.termsfeed.com/live/2b1724ab-a3a7-4bfa-b4aa-0573ee4abcf3"
                                )

                                R.string.feedback -> onLoginPress()
                                R.string.logout -> onLogoutPress()
                            }
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
    SmartAppointmentTheme {
        SettingUI(settings,false, "", listOf(), {}, {}, {}, {}, {}, {}, {}){

        }
    }
}