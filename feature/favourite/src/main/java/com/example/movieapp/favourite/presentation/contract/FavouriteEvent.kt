package com.example.movieapp.favourite.presentation.contract

import com.example.movieapp.domain.model.movie.PopularMovie

sealed interface FavouriteEvent {
    data object OnHomeClick : FavouriteEvent
    data class OnRemoveFavorite(val movie: PopularMovie) : FavouriteEvent
    data class OnMovieClick(val movieId: Int, val category: String) : FavouriteEvent
}