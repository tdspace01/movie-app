package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto
import com.example.movieapp.domain.model.movie.PopularMovie

fun PopularMovieResponseDto.PopularMovieDto.toDomain(genreMap: Map<Int, String> = emptyMap()): PopularMovie {
    val activeMap = genreMap.ifEmpty { MovieMapperConfig.genreMap }
    val genreNames = genreIds.mapNotNull { activeMap[it] }

    return PopularMovie(
        id = this.id,
        title = this.title,
        posterUrl = this.posterPath?.let { "${MovieMapperConfig.POSTER_BASE_URL}$it" },
        year = if (!this.releaseDate.isNullOrBlank()) this.releaseDate.take(4) else "n/a",
        category = genreNames.firstOrNull() ?: "n/a"
    )
}