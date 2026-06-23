package com.example.movieapp.data.remote.model.response_dto

import com.example.movieapp.data.remote.model.request_dto.GenreRequestDto
import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponseDto(
    val genres:List<GenreRequestDto>
)
