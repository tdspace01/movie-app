package com.example.movieapp.favourite.favouritescreen.di

import com.example.movieapp.favourite.favouritescreen.FavoriteViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val favoriteViewModelModule = module {
    viewModelOf(::FavoriteViewModel)
}