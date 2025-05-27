package com.ahsan.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahsan.composable.transformation.NanpVisualTransformation
import com.ahsan.core.Validator

@Composable
fun ThemeTextField(modifier: Modifier = Modifier, label: String = "", value: String = "", isReadOnly: Boolean = false,
                   enabled: Boolean = true, errorMessage: String = "", keyboardType: KeyboardType = KeyboardType.Text,
                   imeAction: ImeAction = ImeAction.Next, trailingIcon: Int = 0, onClick: () -> Unit = {}, onChanged: (text: String) -> Unit) {
    var text by remember {
        mutableStateOf(value)
    }
    if(value.isNotEmpty()){
        text = value
    }
    val numericRegex = Regex("[^0-9]")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(value = text, onValueChange = {
            if (keyboardType == KeyboardType.Phone){
                val stripped = numericRegex.replace(it, "")
                text = if (stripped.length >= 10) {
                    stripped.substring(0..9)
                } else {
                    stripped
                }
                onChanged(text)
            }
            else{
                text = it
                onChanged(text)
            }
        },
            modifier
                .fillMaxWidth()
                .pointerInput(value) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            onClick()
                        }
                    }
                },
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            visualTransformation = when (keyboardType) {
                KeyboardType.Password -> PasswordVisualTransformation()
                KeyboardType.Phone -> NanpVisualTransformation()
                else -> VisualTransformation.None
            },
            readOnly = isReadOnly,
            trailingIcon = {
                if(trailingIcon != 0)
                    Image(painter = painterResource(id = trailingIcon), contentDescription = "", Modifier.clickable {
                        onClick()
                    })
            },
            label = {
                ThemeText(text = label)
            },
        )
        if(errorMessage.isNotEmpty()){
            ThemeText(text = errorMessage, color = Color.Red)
        }
    }
}


@Composable
fun RequiredTextField(label: String, value: String = "", errorMessage: String? = null, keyboardType: KeyboardType = KeyboardType.Text, imeAction: ImeAction = ImeAction.Next, onChanged: (text: String) -> Unit){
    val context = LocalContext.current
    var error by remember { mutableStateOf("") }
    ThemeTextField(label = stringResource(R.string.required_field_label, label), value = value, imeAction = imeAction, keyboardType = keyboardType, errorMessage = error) {
        onChanged(it)
        error = if(errorMessage == null && it.isEmpty()){
            context.getString(R.string.field_required, label)
        } else{
            errorMessage ?: ""
        }
    }
}

@Composable
fun PasswordTextField(isValidationRequired: Boolean = false, onChanged: (text: String) -> Unit){
    val context = LocalContext.current

    var errorMessage by remember {
        mutableStateOf("")
    }
    RequiredTextField(label = stringResource(id = R.string.password), errorMessage = errorMessage, keyboardType = KeyboardType.Password) {
        onChanged(it)
        if(it.length < 5 && isValidationRequired) {
            errorMessage = context.getString(R.string.password_error)
        }
        else{
            errorMessage = ""
        }
    }
}

@Composable
fun EmailTextField(value: String = "", onChanged: (text: String) -> Unit){
    val context = LocalContext.current
    var errorMessage by remember {
        mutableStateOf("")
    }
    RequiredTextField(label = stringResource(id = R.string.email), value = value, keyboardType = KeyboardType.Email, errorMessage = errorMessage) {
        onChanged(it)
        if(!Validator.isValidEmail(it)){
            errorMessage = context.getString(R.string.email_error)
        }
        else{
            errorMessage = ""
        }
    }
}

@Composable
fun PhoneTextField(value: String = "", onChanged: (text: String) -> Unit){
    val context = LocalContext.current
    var errorMessage by remember {
        mutableStateOf("")
    }
    RequiredTextField(label = stringResource(id = R.string.phone_number), value = value, keyboardType = KeyboardType.Phone, errorMessage = errorMessage) {
        onChanged(it)
        if (value.length < 9){
            errorMessage = context.getString(R.string.invalid_phone)
        }
        else if(value.isEmpty())
            errorMessage = context.getString(R.string.field_required, "Phone")
        else
            errorMessage = ""
    }
}

@Composable
fun DisabledTextField(label: String,  errorMessage: String = "", value: String, onClick: () -> Unit){
    ThemeTextField(label = label, errorMessage = errorMessage, isReadOnly = true, value = value, trailingIcon = R.drawable.chevron_down, onClick = onClick) {

    }
}

@Preview
@Composable
fun TextFieldPreview(){
    DisabledTextField("", value = "") {

    }
}