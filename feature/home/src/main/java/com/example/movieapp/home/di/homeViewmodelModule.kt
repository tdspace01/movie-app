package com.example.movieapp.home.di

import com.example.movieapp.home.presentation.vm.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModelOf(::HomeViewModel)
}