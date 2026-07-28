package com.example.movieapp.designsystem.components

import coil.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.movieapp.designsystem.design.MovieAppShapes

@Composable
fun MovieAppAsyncImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier.fillMaxWidth()
            .clip(MovieAppShapes.corner16),
        contentScale = contentScale
    )
}