package com.example.movieapp.data.remote.model.response_dto

import com.example.movieapp.data.remote.model.request_dto.GenreRequestDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponseDto(
    @SerialName("genres") val genres:List<GenreRequestDto>
)
