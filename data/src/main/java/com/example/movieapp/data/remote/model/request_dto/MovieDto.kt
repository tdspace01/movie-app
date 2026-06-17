package com.example.movieapp.data.remote.model.request_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieDto (
    @SerialName("id") val id:Int
)