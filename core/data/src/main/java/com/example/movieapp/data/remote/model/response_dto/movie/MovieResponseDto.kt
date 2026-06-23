package com.example.movieapp.data.remote.model.response_dto.movie

import com.example.movieapp.data.remote.model.request_dto.movie.MovieRequestDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<MovieRequestDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)