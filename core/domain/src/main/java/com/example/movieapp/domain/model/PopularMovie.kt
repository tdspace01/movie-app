package com.example.movieapp.domain.model

data class PopularMovie(
    val id:Int,
    val title: String,
    val posterUrl: String?,
    val overview: String,
    val rating: Double,
    val releaseDate: String
)