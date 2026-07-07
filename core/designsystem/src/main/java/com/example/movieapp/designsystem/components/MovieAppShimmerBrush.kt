package com.example.movieapp.designsystem.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import com.example.movieapp.designsystem.R
import com.example.movieapp.designsystem.theme.DarkColorScheme

@Composable
fun Modifier.shimmerBrush(): Modifier {
    val shimmerColors = listOf(
        DarkColorScheme.black,
        DarkColorScheme.darkGrey.copy(alpha = 0.7f),
        DarkColorScheme.black,
    )

    val transition = rememberInfiniteTransition(
        label = stringResource(R.string.shimmer_transition)
    )

    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = stringResource(R.string.shimmer_transition)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    return this.background(brush)
}