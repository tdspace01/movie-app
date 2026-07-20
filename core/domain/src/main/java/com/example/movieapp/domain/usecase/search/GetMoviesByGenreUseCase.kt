package com.example.movieapp.domain.usecase.search

import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.search.MoviesByGenreRepository
import com.example.movieapp.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetMoviesByGenreUseCase(
    private val repository: MoviesByGenreRepository
): BaseUseCase<Int, List<PopularMovie>>() {
    override fun invoke(params: Int?): Flow<Resource<List<PopularMovie>>> {
        return repository.getMoviesByGenre(
            genreId = params ?: return flowOf(Resource.Error(NetworkError.UNKNOWN))
        )
    }
}