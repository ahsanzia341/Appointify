package com.ahsan.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.core.AppRoute.HomeRoute
import com.ahsan.core.AppRoute.LoginRoute

@Composable
fun WelcomeScreen(navController: NavController) {
    WelcomeUI(onSignInPress = {
        navController.navigate(LoginRoute)
    }){
        navController.navigate(HomeRoute)
    }
}

@Composable
fun WelcomeUI(onSignInPress: () -> Unit, onButtonPress: () -> Unit) {
    Scaffold(topBar = {
        TopBar(
            title = "Welcome to Appointify.",
            navIcon = null
        )
    }, modifier = Modifier.padding(8.dp)) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ThemeText(text = "We aim to provide you a smooth appointment solution for your business. You can use this app for any type of business and start scheduling your appointments. The application will notify you for your upcoming appointments so you reach them on time. We hope you have a very good experience with our app.",
                textAlign = TextAlign.Center)

            ThemeText(text = "To enable your data to sync across all your devices, please login.", textAlign = TextAlign.Center)
            ThemeButton(text = stringResource(id = com.ahsan.composable.R.string.login), modifier = Modifier.fillMaxWidth()) {
                onSignInPress()
            }
            ThemeText(
                text = "OR \n Use the app now and login later.",
                textAlign = TextAlign.Center
            )
            ThemeButton(text = "Proceed without login", modifier = Modifier.fillMaxWidth()) {
                onButtonPress()
            }
        }
    }
}

@Composable
@Preview
fun WelcomePreview(){
    WelcomeUI(onSignInPress = {
    }){

    }
}