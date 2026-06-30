package com.example.movieapp.home.home.di

import com.example.movieapp.home.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModelOf(::HomeViewModel)
}