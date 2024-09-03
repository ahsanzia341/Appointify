package com.ahsan.smartappointment

import android.content.Context
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahsan.core.DestinationRoute
import com.ahsan.smartappointment.ui.theme.SmartAppointmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntryAsState?.destination
            val sharedPref = getSharedPreferences("pref", Context.MODE_PRIVATE)
            var startDestination = DestinationRoute.HOME_ROUTE
            if (sharedPref.getString("isFirstTime", "true") == "true") {
                startDestination = DestinationRoute.WELCOME_ROUTE
                sharedPref.edit().putString("isFirstTime", "false").apply()
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
