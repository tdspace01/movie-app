package com.example.movieapp.domain.usecase

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.PopularMovie
import com.example.movieapp.domain.repository.MoviesByGenreRepository
import kotlinx.coroutines.flow.Flow

class MoviesByGenreUseCase(
    private val repository: MoviesByGenreRepository
) : BaseUseCase<Int, List<PopularMovie>>() {
    override operator fun invoke(params: Int): Flow<Resource<List<PopularMovie>>> {
        return repository.getMoviesByGenre(params)
    }
}
