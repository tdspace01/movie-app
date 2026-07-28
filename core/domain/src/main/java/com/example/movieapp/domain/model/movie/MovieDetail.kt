package com.example.movieapp.domain.model.movie

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val rating: Double,
    val releaseYear: String,
    val durationFormatted: String,
    val isFavorite: Boolean = false
)