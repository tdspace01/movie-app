package com.example.movieapp.data.remote.model.request_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreRequestDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)
