package com.example.movieapp.domain.usecase.search

import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.search.SearchMovieRepository
import com.example.movieapp.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SearchMoviesUseCase(
    private val repository: SearchMovieRepository
): BaseUseCase<String, List<PopularMovie>>()  {
    override fun invoke(params: String?): Flow<Resource<List<PopularMovie>>> {
        return repository.searchMovies(
            query = params ?: return flowOf(Resource.Error(NetworkError.UNKNOWN))
        )
    }
}