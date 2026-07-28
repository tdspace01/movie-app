package com.example.movieapp.designsystem.components

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.runtime.getValue
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import com.example.movieapp.designsystem.R
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.animation.core.infiniteRepeatable
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MovieAppLoader(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loaderRotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = LinearEasing)
        ),
        label = "rotatingAngle"
    )

    Box(
        modifier = modifier.fillMaxSize()
            .background(DarkColorScheme.black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.loader_icon),
            contentDescription = null,
            modifier = Modifier.size(MovieAppSizing.size96).rotate(angle)
        )
    }
}
