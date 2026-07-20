package com.example.movieapp.domain.usecase

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.PopularMovie
import com.example.movieapp.domain.repository.PopularMovieRepository
import kotlinx.coroutines.flow.Flow

class PopularMoviesUseCase(
    private val repository: PopularMovieRepository
) : BaseNoParamUseCase<List<PopularMovie>>() {
    override operator fun invoke(): Flow<Resource<List<PopularMovie>>> {
        return repository.getMovies()
    }
}