package com.example.movieapp.splash.di

import com.example.movieapp.splash.presentation.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashViewModelModule = module{
    viewModelOf(::SplashViewModel)
}