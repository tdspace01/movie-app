package com.example.movieapp.splash.presentation

sealed interface SplashEvent {
    object StartTimer : SplashEvent
}