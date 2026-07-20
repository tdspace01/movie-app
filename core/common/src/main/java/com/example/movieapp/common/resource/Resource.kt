package com.example.movieapp.common.resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorType: NetworkError, val message: String? = null) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
}
