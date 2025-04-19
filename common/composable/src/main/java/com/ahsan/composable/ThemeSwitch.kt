package com.ahsan.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahsan.composable.theme.SmartAppointmentTheme

@Composable
fun ThemeSwitch(label: String, onCheckChanged: (Boolean) -> Unit){
    var checkChanged by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        ThemeText(text = label)
        Switch(checked = checkChanged, onCheckedChange = {
            checkChanged = it
            onCheckChanged(it)
        })
    }
}

@Composable
@Preview
fun SwitchPreview(){
    SmartAppointmentTheme {
        ThemeSwitch("My Switch"){}
    }
}