package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.response_dto.movie.MovieDetailResponseDto
import com.example.movieapp.domain.model.movie.MovieDetail
import kotlin.math.roundToInt

private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
private const val ORIGINAL_IMAGE_URL = "https://image.tmdb.org/t/p/original"

fun MovieDetailResponseDto.toDomain(): MovieDetail {
    val formattedDuration = runtime?.let { minutes ->
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        if (hours > 0) "${hours}h $remainingMinutes" else "$remainingMinutes"
    } ?: "N/A"

    val roundedRating = (this.voteAverage * 10).roundToInt() / 10.0

    return MovieDetail(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterPath?.let { "$BASE_IMAGE_URL$it" },
        backdropUrl = this.backdropPath?.let { "$ORIGINAL_IMAGE_URL$it" },
        rating = roundedRating,
        releaseYear = if (!this.releaseDate.isNullOrBlank()) this.releaseDate.take(4) else "N/A",
        durationFormatted = formattedDuration
    )
}