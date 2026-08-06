package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto
import com.example.movieapp.domain.model.movie.PopularMovie

fun PopularMovieResponseDto.PopularMovieDto.toDomain(): PopularMovie {
    val category = genreIds
        .firstNotNullOfOrNull { MovieMapperConfig.genreMap[it] }
        ?: "n/a"

    return PopularMovie(
        id = this.id,
        title = this.title,
        posterUrl = posterPath?.let { "${MovieMapperConfig.POSTER_BASE_URL}$it" },
        year = releaseDate?.take(4).takeUnless { it.isNullOrBlank() } ?: "n/a",
        category = category,
    )
}