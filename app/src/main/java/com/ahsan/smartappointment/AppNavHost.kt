package com.ahsan.smartappointment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.createGraph
import com.ahsan.accountsettings.accountSettingsNavigation
import com.ahsan.appointment.appointmentNavigation
import com.ahsan.appointmenthistory.appointmentHistoryNavigation
import com.ahsan.authentication.authenticationNavigation
import com.ahsan.backup.backupNavigation
import com.ahsan.business.createBusinessNavigation
import com.ahsan.changepassword.changePasswordNavigation
import com.ahsan.client.clientNavigation
import com.ahsan.currency.currencyNavigation
import com.ahsan.home.homeNavigation
import com.ahsan.service.serviceNavigation
import com.ahsan.setting.settingNavigation
import com.ahsan.webview.webViewNavigation

/**
 * Created by anwaralhasan  on 2/14/2024.
 */

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Any
) {
    val navGraph = remember(navController){
        navController.createGraph(startDestination = startDestination) {
            accountSettingsNavigation(navController)
            authenticationNavigation(navController)
            appointmentNavigation(navController)
            appointmentHistoryNavigation(navController)
            backupNavigation(navController)
            createBusinessNavigation(navController)
            changePasswordNavigation(navController)
            homeNavigation(navController)
            currencyNavigation(navController)
            clientNavigation(navController)
            settingNavigation(navController)
            serviceNavigation(navController)
            webViewNavigation(navController)
        }
    }
    NavHost(navController, navGraph)
}