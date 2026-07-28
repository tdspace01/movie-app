package com.example.movieapp.moviedetail.moviedetailscreen.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import com.example.movieapp.moviedetail.moviedetailscreen.MovieDetailViewModel

val movieDetailViewModelModule = module{
    viewModelOf(::MovieDetailViewModel)
}