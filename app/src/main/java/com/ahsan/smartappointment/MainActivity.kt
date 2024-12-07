package com.ahsan.smartappointment

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahsan.core.AppRoute.AppointmentHistoryRoute
import com.ahsan.core.BillingObject
import com.ahsan.core.AppRoute.ClientListRoute
import com.ahsan.core.Constant
import com.ahsan.core.AppRoute.HomeRoute
import com.ahsan.core.AppRoute.ServiceListRoute
import com.ahsan.core.AppRoute.SettingsRoute
import com.ahsan.core.AppRoute.WelcomeRoute
import com.ahsan.smartappointment.ui.theme.SmartAppointmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BillingObject.setupBilling(this)
            val navController = rememberNavController()
            val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntryAsState?.destination
            val sharedPref = getSharedPreferences(Constant.SHARED_PREF_KEY, Context.MODE_PRIVATE)
            var startDestination: Any = HomeRoute
            if (sharedPref.getString("isFirstTime", "true") == "true") {
                startDestination = WelcomeRoute
                sharedPref.edit().putString("isFirstTime", "false").apply()
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
            }
            SmartAppointmentTheme {
                Scaffold(bottomBar = {
                    if (currentDestination?.let {
                            it.hasRoute(HomeRoute::class) || it.hasRoute(ClientListRoute::class)
                                    || it.hasRoute(SettingsRoute::class) || it.hasRoute(AppointmentHistoryRoute::class)
                                    || it.hasRoute(ServiceListRoute::class)
                        } == true) {
                        BottomBar(navController = navController, currentDestination.route ?: "")
                    }
                }) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        AppNavHost(navController = navController, startDestination)
                    }
                }
            }
        }
    }
}
