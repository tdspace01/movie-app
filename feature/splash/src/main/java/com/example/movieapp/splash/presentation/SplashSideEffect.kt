package com.example.movieapp.splash.presentation

sealed interface SplashSideEffect {
    object NavigateToHome : SplashSideEffect
}