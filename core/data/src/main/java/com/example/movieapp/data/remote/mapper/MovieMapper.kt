package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.request_dto.movie.PopularMovieDto
import com.example.movieapp.domain.model.movie.PopularMovie

private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
var globalGenreMap: Map<Int, String> = emptyMap()
fun PopularMovieDto.toDomain(genreMap: Map<Int, String> = emptyMap()): PopularMovie {
    val activeMap = genreMap.ifEmpty { globalGenreMap }
    val genreNames = genreIds.mapNotNull { activeMap[it] }

    return PopularMovie(
        id = this.id,
        title = this.title,
        posterUrl = this.posterPath?.let { "$BASE_IMAGE_URL$it" },
        year = if (!this.releaseDate.isNullOrBlank()) this.releaseDate.take(4) else "N/A",
        category = genreNames.firstOrNull() ?: "N/A"
    )
}