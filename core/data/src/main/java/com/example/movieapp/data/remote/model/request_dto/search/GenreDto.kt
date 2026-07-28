package com.example.movieapp.data.remote.model.request_dto.search

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
     val id: Int,
     val name: String,
)