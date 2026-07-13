package com.example.movieapp.data.remote.model.search

import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMovieResponseDto(
    val page: Int,
    val results: List<PopularMovieResponseDto.PopularMovieDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)