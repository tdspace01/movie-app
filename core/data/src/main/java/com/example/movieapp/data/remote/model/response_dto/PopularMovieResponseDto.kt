package com.example.movieapp.data.remote.model.response_dto

import com.example.movieapp.data.remote.model.request_dto.PopularMovieRequestDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMovieResponseDto(
    val page: Int,
    val results: List<PopularMovieRequestDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)