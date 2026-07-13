package com.example.movieapp.favourite.presentation

import com.example.movieapp.domain.model.movie.PopularMovie

data class FavouriteState(
    val favoriteMovies: List<PopularMovie> = emptyList()
)