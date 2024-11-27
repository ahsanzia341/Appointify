package com.ahsan.setting

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahsan.composable.ThemeText

@Composable
fun SettingRowUI(text: String, isNextPage: Boolean = false, onClick: () -> Unit = {}) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }) {

        Box(Modifier.fillMaxWidth()) {
            ThemeText(text = text, modifier = Modifier.align(Alignment.CenterStart))
            if(isNextPage){
                Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight, contentDescription = "Next",
                    modifier = Modifier.align(Alignment.CenterEnd))
            }
        }

        HorizontalDivider(color = Color.Black, thickness = 1.dp,
            modifier = Modifier
                .padding(top = 8.dp))
    }
}

@Composable
@Preview
fun PreviewSettingRowUI(){
    Column(Modifier.background(Color.White)) {
        SettingRowUI(text = "Setting", isNextPage = true)
    }
}