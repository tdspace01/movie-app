package com.example.movieapp.data.remote.model.response_dto.search

import com.example.movieapp.data.remote.model.request_dto.search.GenreDto
import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponseDto(
    val genres: List<GenreDto>
)