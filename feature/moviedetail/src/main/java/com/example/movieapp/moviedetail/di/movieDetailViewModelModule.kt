package com.example.movieapp.moviedetail.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import com.example.movieapp.moviedetail.presentation.MovieDetailViewModel

val movieDetailViewModelModule = module {
    viewModelOf(::MovieDetailViewModel)
}