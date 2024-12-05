package com.ahsan.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ThemeButton(modifier: Modifier = Modifier, text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(modifier = modifier
        .padding(start = 16.dp, end = 16.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp), onClick = {
        onClick()
    }, enabled = enabled){
        ThemeText(text = text)
    }
}

@Composable
fun GoogleSignInButton(onClick: () -> Unit){
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(6.dp),

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_google_icon),
            contentDescription = "Google icon"
        )
        ThemeText(text = "Sign in with Google", modifier = Modifier.padding(6.dp))
    }
}

@Preview
@Composable
fun ThemeButtonPreview(){
    GoogleSignInButton() {  }
}