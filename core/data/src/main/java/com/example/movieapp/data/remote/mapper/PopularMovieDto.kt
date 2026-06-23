package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.request_dto.PopularMovieRequestDto
import com.example.movieapp.domain.model.PopularMovie

fun PopularMovieRequestDto.toDomain(): PopularMovie {
    val baseImageUrl = "https://image.tmdb.org/t/p/w500"
    return PopularMovie(
        id = this.id,
        title = this.title,
        posterUrl = this.posterPath?.let{"$baseImageUrl$it"},
        overview = this.overview,
        rating = this.voteAverage,
        releaseDate = this.releaseDate,
    )
}