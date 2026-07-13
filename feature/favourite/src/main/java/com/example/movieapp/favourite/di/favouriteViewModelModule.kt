package com.example.movieapp.favourite.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import com.example.movieapp.favourite.presentation.FavouriteViewModel

val favoriteViewModelModule = module {
    viewModelOf(::FavouriteViewModel)
}