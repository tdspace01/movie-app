package com.example.movieapp.splash.splashscreen

sealed interface SplashEvent{
    object StartTimer: SplashEvent
}