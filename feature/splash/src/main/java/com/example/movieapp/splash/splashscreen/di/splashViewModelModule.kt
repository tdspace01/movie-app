package com.example.movieapp.splash.splashscreen.di

import com.example.movieapp.splash.splashscreen.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashViewModelModule = module{
    viewModelOf(::SplashViewModel)
}