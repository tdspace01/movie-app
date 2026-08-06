package com.example.movieapp.home.presentation.contract

sealed interface HomeSideEffect {
    data object NavigateToFavorite : HomeSideEffect
    data class NavigateToDetail(val movieId: Int, val category: String) : HomeSideEffect
}