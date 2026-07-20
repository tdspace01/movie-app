package com.example.movieapp.splash.presentation.contract

sealed interface SplashSideEffect {
    object NavigateToHome : SplashSideEffect
}