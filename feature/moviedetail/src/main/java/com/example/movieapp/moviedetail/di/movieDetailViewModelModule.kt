package com.example.movieapp.moviedetail.di

import com.example.movieapp.moviedetail.presentation.vm.MovieDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val movieDetailViewModelModule = module {
    viewModelOf(::MovieDetailViewModel)
}