package com.example.movieapp.data.remote.model.response_dto

import com.example.movieapp.data.remote.model.request_dto.PopularMovieRequestDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMovieResponseDto(
    @SerialName("page") val page:Int,
    @SerialName("results") val results:List<PopularMovieRequestDto>,
    @SerialName("total_page") val totalPages:Int,
    @SerialName("total_results") val totalResults:Int,
)