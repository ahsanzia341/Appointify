package com.ahsan.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
private fun ThemeDialog(title: String, text: String, okClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(8.dp)) {
        ThemeHeaderText(text = title)
        ThemeText(text = text)
        ThemeButton(text = stringResource(id = R.string.ok)) {
            okClick()
        }
    }
}

@Composable
fun InfoDialog(title: String, text: String, onDismiss: () -> Unit){
    AlertDialog(
        title = {
            ThemeText(text = title)
        },
        text = {
            ThemeText(text = text)
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                ThemeText(text = "Confirm")
            }
        }
    )
}

@Composable
fun ConfirmationDialog(
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        title = {
            ThemeText(text = title)
        },
        text = {
            ThemeText(text = text)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                ThemeText(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                ThemeText(text = "Dismiss")
            }
        }
    )
}

@Composable
@Preview
fun DialogPreview(){
    ConfirmationDialog(title = "", text = "", {}, {})
}