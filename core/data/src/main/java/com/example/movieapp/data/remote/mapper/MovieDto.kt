package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.request_dto.movie.MovieRequestDto
import com.example.movieapp.domain.model.movie.Movie

private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
var globalGenreMap: Map<Int, String> = emptyMap()
fun MovieRequestDto.toDomain(genreMap: Map<Int, String> = emptyMap()): Movie {
    val activeMap = genreMap.ifEmpty { globalGenreMap }
    val genreNames = genreIds.mapNotNull { activeMap[it] }

    return Movie(
        id = this.id,
        title = this.title,
        posterUrl = this.posterPath?.let { "$BASE_IMAGE_URL$it" },
        year = if (!this.releaseDate.isNullOrBlank()) this.releaseDate.take(4) else "N/A",
        category = genreNames.firstOrNull() ?: "N/A"
    )
}