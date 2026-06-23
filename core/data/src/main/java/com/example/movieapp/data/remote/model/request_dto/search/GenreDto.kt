package com.example.movieapp.data.remote.model.request_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreRequestDto(
     val id: Int,
     val name: String,
)
