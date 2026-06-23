package com.example.movieapp.domain.usecase

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.PopularMovie
import com.example.movieapp.domain.repository.SearchMovieRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(
    private val repository: SearchMovieRepository
) : BaseUseCase<String, List<PopularMovie>>() {
    override operator fun invoke(params: String): Flow<Resource<List<PopularMovie>>> {
        return repository.searchMovies(params)
    }
}