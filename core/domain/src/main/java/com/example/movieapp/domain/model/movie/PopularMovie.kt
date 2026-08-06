package com.example.movieapp.domain.model.movie

data class PopularMovie(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val year: String,
    val category: String,
    val isFavorite: Boolean = false
)