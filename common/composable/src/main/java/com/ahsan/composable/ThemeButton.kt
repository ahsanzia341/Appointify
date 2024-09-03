package com.ahsan.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ThemeButton(modifier: Modifier = Modifier, text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(modifier = modifier.fillMaxWidth(0.75f), onClick = {
        onClick()
    }, enabled = enabled){
        ThemeText(text = text)
    }
}

@Preview
@Composable
fun ThemeButtonPreview(){
    ThemeButton(text = "Button") {  }
}