package com.ahsan.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ThemeFloatingActionButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(modifier = modifier, onClick = {
        onClick()
    }){
        Icon(Icons.Default.Add, contentDescription = "content description")
    }
}

@Preview
@Composable
fun ThemePreview(){
    ThemeFloatingActionButton(modifier = Modifier) {  }
}