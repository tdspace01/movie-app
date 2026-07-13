package com.example.movieapp.home.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import com.example.movieapp.home.presentation.HomeViewModel

val homeViewModelModule = module {
    viewModelOf(::HomeViewModel)
}