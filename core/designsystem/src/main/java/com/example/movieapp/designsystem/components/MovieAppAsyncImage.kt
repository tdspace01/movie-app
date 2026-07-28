package com.example.movieapp.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.designsystem.R
import com.example.movieapp.designsystem.design.MovieAppShapes

@Composable
fun MovieAppAsyncImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val imageModifier = modifier
        .fillMaxWidth()
        .clip(MovieAppShapes.corner16)

    if (imageUrl.isNullOrEmpty()) {
        Box(
            modifier = imageModifier
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_no_image_placeholder),
                contentDescription = contentDescription,
                modifier = Modifier.size(36.dp))
        }
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = imageModifier,
            contentScale = contentScale
        )
    }
}