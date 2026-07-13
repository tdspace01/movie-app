package com.example.movieapp.home.presentation

sealed interface HomeSideEffect {
    object NavigateToFavorite : HomeSideEffect
    data class NavigateToDetail(val movieId: Int, val category: String) : HomeSideEffect
}