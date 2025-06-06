package com.ahsan.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BaseSettingRowUI(modifier: Modifier = Modifier, text: String, uiView: @Composable () -> Unit){
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)) {

        Box(Modifier.fillMaxWidth()) {
            ThemeText(text = text, modifier = Modifier.align(Alignment.CenterStart))
            uiView()
        }

        HorizontalDivider(color = Color.Black, thickness = 1.dp,
            modifier = Modifier
                .padding(top = 8.dp))
    }
}
@Composable
fun SettingRowUI(text: String, isNextPage: Boolean = false, onClick: () -> Unit = {}) {
    BaseSettingRowUI(modifier = Modifier.clickable { onClick() }, text = text) {
        Box(Modifier.fillMaxWidth()) {
            if (isNextPage) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Next",
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewSettingRowUI(){
    Column(Modifier.background(Color.White)) {
        SettingRowUI(text = "Setting", isNextPage = true)
    }
}