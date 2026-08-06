package com.example.movieapp.domain.usecase.common

import com.example.movieapp.common.resource.NetworkResource
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<in Params, out Result> {
    abstract operator fun invoke(params: Params): Flow<NetworkResource<Result>>
}