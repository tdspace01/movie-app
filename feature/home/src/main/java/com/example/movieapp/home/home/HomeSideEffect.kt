package com.example.movieapp.home.home

sealed interface HomeSideEffect {
    object NavigateToFavorite : HomeSideEffect
    data class NavigateToDetail(val movieId: Int, val category: String) : HomeSideEffect
}