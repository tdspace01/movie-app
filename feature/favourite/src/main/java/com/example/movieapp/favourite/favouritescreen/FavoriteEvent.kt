package com.example.movieapp.favourite.favouritescreen

import com.example.movieapp.domain.model.movie.PopularMovie

sealed interface FavoriteEvent{
    object OnHomeClick: FavoriteEvent
    object ObserveFavorites: FavoriteEvent
    data class OnRemoveFavorite(val movie: PopularMovie): FavoriteEvent
    data class OnMovieClick(val movieId:Int,val category: String): FavoriteEvent
}