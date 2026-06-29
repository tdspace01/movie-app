package com.example.movieapp.data.remote.model.response_dto.movie

import com.example.movieapp.data.remote.model.request_dto.movie.PopularMovieDto
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
)