package com.example.movieapp.designsystem.theme

import androidx.compose.ui.graphics.Color

data class MovieAppColorScheme(
    val grey: Color,
    val black: Color,
    val whisper: Color,
    val isDark: Boolean,
    val darkGrey: Color,
    val lightGrey: Color,
    val darkestGrey: Color,
    val lighterGrey: Color,
    val lightestGrey: Color,
    val primaryYellow: Color,
)

val DarkColorScheme = MovieAppColorScheme(
    isDark = true,
    grey = Color(0xFF808080),
    black = Color(0xFF080808),
    whisper = Color(0xFFEAEAEA),
    darkGrey = Color(0xFF5D5D5D),
    lightGrey = Color(0xFFA5A5A5),
    darkestGrey = Color(0xFF1C1C1C),
    lighterGrey = Color(0xFFCACACA),
    lightestGrey = Color(0xFFDEDEDE),
    primaryYellow = Color(0xFFFFC44A),
)

val LightColorScheme = MovieAppColorScheme(
    isDark = true,
    grey = Color(0xFF808080),
    black = Color(0xFF080808),
    whisper = Color(0xFFEAEAEA),
    darkGrey = Color(0xFF5D5D5D),
    lightGrey = Color(0xFFA5A5A5),
    darkestGrey = Color(0xFF1C1C1C),
    lighterGrey = Color(0xFFCACACA),
    lightestGrey = Color(0xFFDEDEDE),
    primaryYellow = Color(0xFFFFC44A),
)