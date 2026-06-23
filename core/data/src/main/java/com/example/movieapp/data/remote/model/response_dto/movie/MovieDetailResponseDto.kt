package com.example.movieapp.data.remote.model.response_dto.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponseDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("runtime") val runtime: Int? = null
)
