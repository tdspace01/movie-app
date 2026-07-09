package com.example.movieapp.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import com.example.movieapp.designsystem.R
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing

@Composable
fun MovieAppAsyncImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val imageModifier = modifier
        .fillMaxSize()
        .clip(MovieAppShapes.corner16)

    if (imageUrl.isNullOrEmpty()) {
        PlaceholderBox(modifier = imageModifier)
    } else {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = imageModifier,
            contentScale = contentScale,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmerBrush()
                )
            },
            error = {
                PlaceholderBox(modifier = Modifier.fillMaxSize())
            },
            success = { state ->
                Image(
                    painter = state.painter,
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale
                )
            }
        )
    }
}

@Composable
private fun PlaceholderBox(
    modifier: Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_no_image_placeholder),
            contentDescription = null,
            modifier = Modifier.size(MovieAppSizing.size36)
        )
    }
}