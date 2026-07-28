package com.example.movieapp.domain.usecase

import com.example.movieapp.common.resource.Resource
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<in Params, out Result> {
    abstract operator fun invoke(params: Params): Flow<Resource<Result>>
}

abstract class BaseNoParamUseCase<out Result> {
    abstract operator fun invoke(): Flow<Resource<Result>>
}