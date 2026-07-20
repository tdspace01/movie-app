package com.example.movieapp.domain.usecase.base

import com.example.movieapp.common.resource.Resource
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<in Params,out Result> {
    abstract operator fun invoke(params:Params? = null): Flow<Resource<Result>>
}