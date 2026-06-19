package com.example.movieapp.common.resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorType: NetworkError, val message: String? = null) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
}

fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(errorType,message)
        is Resource.Loading -> Resource.Loading(isLoading)
    }
}

fun <T,R> Flow<Resource<T>>.asResource(transform: (T) -> R):Flow<Resource<R>>{
    return this.map{ resource->
        resource.map(transform)
    }
}