package com.example.movieapp.data.remote.model.request_dto.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PopularMovieDto (
    val id:Int,
    val title: String,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("genre_ids")
    val genreIds:List<Int> = emptyList(),
)