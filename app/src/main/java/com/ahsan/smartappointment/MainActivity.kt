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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahsan.core.BillingObject
import com.ahsan.core.Constant
import com.ahsan.core.DestinationRoute
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
            var startDestination = DestinationRoute.HOME_ROUTE
            if (sharedPref.getString("isFirstTime", "true") == "true") {
                startDestination = DestinationRoute.WELCOME_ROUTE
                sharedPref.edit().putString("isFirstTime", "false").apply()
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
            }
            SmartAppointmentTheme {
                Scaffold(bottomBar = {
                    if (currentDestination?.route.let {
                            it == DestinationRoute.HOME_ROUTE || it == DestinationRoute.CLIENT_LIST_ROUTE
                                    || it == DestinationRoute.SETTINGS_ROUTE || it == DestinationRoute.APPOINTMENT_HISTORY_ROUTE
                                    || it == DestinationRoute.SERVICE_LIST_ROUTE
                        }) {
                        BottomBar(navController = navController, currentDestination?.route ?: "")
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
