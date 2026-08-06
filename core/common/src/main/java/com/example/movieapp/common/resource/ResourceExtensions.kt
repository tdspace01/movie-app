package com.example.movieapp.common.resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, R> NetworkResource<T>.map(transform: (T) -> R): NetworkResource<R> {
    return when (this) {
        is NetworkResource.Success -> NetworkResource.Success(transform(data))
        is NetworkResource.Error -> NetworkResource.Error(errorType, message)
        is NetworkResource.Loading -> NetworkResource.Loading(isLoading)
    }
}

fun <T, R> Flow<NetworkResource<T>>.asResource(transform: (T) -> R): Flow<NetworkResource<R>> {
    return this.map { resource ->
        resource.map(transform)
    }
}

suspend fun <T> Flow<NetworkResource<T>>.collectAsResource(
    onLoading: (Boolean) -> Unit = {},
    onError: (NetworkError) -> Unit = {},
    onSuccess: (T) -> Unit = {}
) {
    this.collect { resource ->
        when (resource) {
            is NetworkResource.Loading -> onLoading(resource.isLoading)
            is NetworkResource.Success -> onSuccess(resource.data)
            is NetworkResource.Error -> onError(resource.errorType)
        }
    }
}