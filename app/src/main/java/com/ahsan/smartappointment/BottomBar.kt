package com.ahsan.smartappointment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahsan.composable.ThemeText
import com.ahsan.core.AppRoute.SettingsRoute
import com.ahsan.theme.PrimaryColor

@Composable
fun BottomBar(navController: NavController, currentDestination: String) {
    BottomAppBar(containerColor = PrimaryColor, contentColor = Color.White){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomBarDestination.entries.forEach {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(if(it.route == currentDestination) it.filledIcon else it.unFilledIcon, "", modifier = Modifier.clickable {
                        navController.navigate(it.route)
                    })
                    ThemeText(text = stringResource(id = it.title))
                }
            }
        }
    }
}

@Preview
@Composable
fun BottomBarPreview(){
    BottomBar(navController = rememberNavController(), SettingsRoute.toString())
}