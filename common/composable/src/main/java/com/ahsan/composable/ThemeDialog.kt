package com.ahsan.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ThemeDialog(title: String, text: String, okClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ThemeHeaderText(text = title)
        ThemeText(text = text)
        ThemeButton(text = stringResource(id = R.string.ok)) {
            okClick()
        }
    }
}

@Composable
@Preview
fun DialogPreview(){
    ThemeDialog(title = "", text = ""){}
}