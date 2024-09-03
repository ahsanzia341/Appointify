package com.ahsan.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ThemeTextField(modifier: Modifier = Modifier, label: String = "", value: String = "", isReadOnly: Boolean = false,
                   isError: Boolean = false, enabled: Boolean = true, keyboardType: KeyboardType = KeyboardType.Text, trailingIcon: Int = 0, onClick: () -> Unit = {}, onChanged: (text: String) -> Unit) {
    var text by remember {
        mutableStateOf(value)
    }
    if(value.isNotEmpty()){
        text = value
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(value = text, onValueChange = {
            text = it
            onChanged(text)
        }, modifier
            .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .onFocusChanged {
                if (it.hasFocus) {
                    onClick()
                }
            },
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            readOnly = isReadOnly,
            trailingIcon = {
                if(trailingIcon != 0)
                    Image(painter = painterResource(id = trailingIcon), contentDescription = "", Modifier.clickable {
                        onClick()
                    })
            },
            label = {
                ThemeText(text = label)
            }, shape = RoundedCornerShape(4.dp),
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.White, unfocusedIndicatorColor = Color.White,
                errorContainerColor = Color.White)
        )
        if(isError){
            ThemeText(text = "$label field is required.", color = Color.Red)
        }
    }

}

@Preview
@Composable
fun TextFieldPreview(){
    ThemeTextField {

    }
}