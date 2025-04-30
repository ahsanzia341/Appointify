package com.ahsan.composable

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ThemeImageButton(modifier: Modifier = Modifier, imagePath: Uri?, onClick: () -> Unit){
    AsyncImage(
        model = imagePath ?: R.drawable.placeholder_image,
        contentDescription = "logo",
        contentScale = ContentScale.Crop,            // crop the image if it's not a square
        modifier = modifier
            .size(82.dp)
            .clip(CircleShape)                       // clip to the circle shape
            .border(2.dp, Color.Gray, CircleShape)
            .clickable {
                onClick()
            }
    )
}