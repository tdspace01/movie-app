package com.example.movieapp.common.resource

sealed class NetworkResource<out T> {
    data class Success<out T>(val data: T) : NetworkResource<T>()
    data class Error(val errorType: NetworkError, val message: String? = null) :
        NetworkResource<Nothing>()

    data class Loading(val isLoading: Boolean) : NetworkResource<Nothing>()
}