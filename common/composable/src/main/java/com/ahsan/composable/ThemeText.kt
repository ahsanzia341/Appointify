package com.ahsan.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ThemeText(modifier: Modifier = Modifier, text: String, color: Color = Color.Unspecified,
              textAlign: TextAlign = TextAlign.Unspecified, textStyle: TextStyle = MaterialTheme.typography.labelMedium) {
    Text(text = text, modifier = modifier, style = textStyle, color = color, textAlign = textAlign)
}

@Composable
fun ThemeHeaderText(modifier: Modifier = Modifier, text: String){
    ThemeText(text = text, modifier = modifier, textStyle = MaterialTheme.typography.titleLarge)
}

@Composable
fun ErrorText(modifier: Modifier = Modifier, text: String){
    ThemeText(modifier, text = text, color = Color.Red)
}