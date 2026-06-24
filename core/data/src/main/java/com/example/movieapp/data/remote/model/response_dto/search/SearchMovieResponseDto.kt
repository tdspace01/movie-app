package com.example.movieapp.data.remote.model.response_dto.search

import com.example.movieapp.data.remote.model.request_dto.movie.PopularMovieRequestDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMovieResponseDto(
    val page: Int,
    val results: List<PopularMovieRequestDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
