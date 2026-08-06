package com.example.movieapp.navigation.moviedetail

import kotlinx.serialization.Serializable

sealed interface MovieDetailRoute {
    @Serializable
    data class MovieDetail(
        val movieId: Int,
        val category: String
    ) : MovieDetailRoute
}