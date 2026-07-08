package com.example.movieapp.data.remote.mapper

object MovieMapperConfig {
    private const val BASE = "https://image.tmdb.org/t/p/"
    const val POSTER_BASE_URL: String = "${BASE}w500"
    const val BACKDROP_BASE_URL: String = "${BASE}original"
    var genreMap: Map<Int, String> = emptyMap()
}