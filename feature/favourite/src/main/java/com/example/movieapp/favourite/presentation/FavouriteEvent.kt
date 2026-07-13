package com.example.movieapp.favourite.presentation

import com.example.movieapp.domain.model.movie.PopularMovie

sealed interface FavouriteEvent {
    object OnHomeClick : FavouriteEvent
    object ObserveFavorites : FavouriteEvent
    data class OnRemoveFavorite(val movie: PopularMovie) : FavouriteEvent
    data class OnMovieClick(val movieId: Int, val category: String) : FavouriteEvent
}