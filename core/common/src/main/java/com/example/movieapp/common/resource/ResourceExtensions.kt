package com.example.movieapp.common.resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

suspend fun <T> Flow<Resource<T>>.collectAsResource(
    onLoading: (Boolean) -> Unit = {},
    onError: (NetworkError) -> Unit = {},
    onSuccess: (T) -> Unit = {}
) {
    this.collect { resource ->
        when (resource) {
            is Resource.Loading -> onLoading(resource.isLoading)
            is Resource.Success -> onSuccess(resource.data)
            is Resource.Error -> onError(resource.errorType)
        }
    }
}