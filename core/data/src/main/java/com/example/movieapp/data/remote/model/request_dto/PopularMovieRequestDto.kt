package com.example.movieapp.data.remote.model.request_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PopularMovieRequestDto (
    val id:Int,
    val title: String,
    @SerialName("poster_path") val posterPath: String? = null,
    val overview: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("release_date") val releaseDate: String,
)