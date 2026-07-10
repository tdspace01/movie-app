package com.example.movieapp.designsystem.theme

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalMovieAppColor = staticCompositionLocalOf { DarkColorScheme }

object MovieAppTheme {
    val colors: MovieAppColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalMovieAppColor.current
}

@Composable
fun MovieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    SystemThem(lightIcons = !darkTheme)

    CompositionLocalProvider(LocalMovieAppColor provides colorScheme) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(colorScheme.black)
        ) {
            content()
        }
    }
}