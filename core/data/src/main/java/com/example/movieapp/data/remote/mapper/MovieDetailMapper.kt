package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.movie.MovieDetailResponseDto
import com.example.movieapp.domain.model.movie.MovieDetail
import kotlin.math.roundToInt

fun MovieDetailResponseDto.toDomain(): MovieDetail {
    val formattedDuration = runtime?.let { minutes ->
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        if (hours > 0) "${hours}h ${remainingMinutes}m" else "${remainingMinutes}m"
    } ?: "n/a"

    val roundedRating = (this.voteAverage * 10).roundToInt() / 10.0

    return MovieDetail(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterPath?.let { "${MovieMapperConfig.POSTER_BASE_URL}$it" },
        backdropUrl = this.backdropPath?.let { "${MovieMapperConfig.BACKDROP_BASE_URL}$it" },
        rating = roundedRating,
        releaseYear = if (!this.releaseDate.isNullOrBlank()) this.releaseDate.take(4) else "n/a",
        durationFormatted = formattedDuration
    )
}