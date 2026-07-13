package com.example.movieapp.favourite.presentation

sealed interface FavouriteSideEffect {
    object NavigateToHome : FavouriteSideEffect
    data class NavigateToDetail(val movieId: Int, val category: String) : FavouriteSideEffect
}