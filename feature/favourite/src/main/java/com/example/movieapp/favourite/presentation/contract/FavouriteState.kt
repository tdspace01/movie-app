package com.example.movieapp.favourite.presentation.contract

import com.example.movieapp.domain.model.movie.PopularMovie

data class FavouriteState(
    val favoriteMovies: List<PopularMovie> = emptyList(),
) {
    val isEmpty: Boolean
        get() = favoriteMovies.isEmpty()
}