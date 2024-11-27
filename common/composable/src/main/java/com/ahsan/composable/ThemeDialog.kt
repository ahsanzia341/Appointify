package com.ahsan.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

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