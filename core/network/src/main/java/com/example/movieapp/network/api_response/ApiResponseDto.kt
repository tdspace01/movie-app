package com.example.movieapp.network.api_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseDto<T>(
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: T? = null
)
