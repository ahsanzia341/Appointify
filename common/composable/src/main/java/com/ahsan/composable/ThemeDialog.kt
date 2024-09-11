package com.ahsan.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ThemeDialog(title: String, text: String, okClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(8.dp)) {
        ThemeHeaderText(text = title)
        ThemeText(text = text)
        ThemeButton(text = stringResource(id = R.string.ok)) {
            okClick()
        }
    }
}

@Composable
fun ConfirmationDialog(title: String, text: String, positiveClick: () -> Unit, negativeClick: () -> Unit, onDismiss: () -> Unit){
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(8.dp).background(
            Color.White)) {
            ThemeHeaderText(text = title)
            ThemeText(text = text)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ThemeButton(text = stringResource(id = R.string.yes)) {
                    positiveClick()
                }
                ThemeButton(text = stringResource(id = R.string.no)) {
                    negativeClick()
                }
            }
        }
    }
}

@Composable
@Preview
fun DialogPreview(){
    ConfirmationDialog(title = "", text = "", {}, {}){}
}