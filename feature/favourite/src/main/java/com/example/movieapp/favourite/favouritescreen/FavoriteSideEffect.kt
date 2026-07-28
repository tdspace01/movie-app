package com.example.movieapp.favourite.favouritescreen

sealed interface FavoriteSideEffect{
    object NavigateToHome: FavoriteSideEffect
    data class NavigateToDetail(val movieId:Int,val category: String): FavoriteSideEffect
}