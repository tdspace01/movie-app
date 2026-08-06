package com.example.movieapp.favourite.presentation.contract

sealed interface FavouriteSideEffect {
    data object NavigateToHome : FavouriteSideEffect
    data class NavigateToDetail(val movieId: Int, val category: String) : FavouriteSideEffect
}