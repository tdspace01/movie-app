package com.example.movieapp.data.remote.model.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMovieResponseDto(
    val page: Int,
    val results: List<PopularMovieDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
) {
    @Serializable
    data class PopularMovieDto(
        val id: Int,
        val title: String,
        @SerialName("poster_path")
        val posterPath: String? = null,
        @SerialName("release_date")
        val releaseDate: String? = null,
        @SerialName("genre_ids")
        val genreIds: List<Int> = emptyList(),
    )
}