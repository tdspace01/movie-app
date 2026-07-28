package com.example.movieapp.favourite.favouritescreen.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import com.example.movieapp.favourite.favouritescreen.FavoriteViewModel

val favoriteViewModelModule = module {
    viewModelOf(::FavoriteViewModel)
}