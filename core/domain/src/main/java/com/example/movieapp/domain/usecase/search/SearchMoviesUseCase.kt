package com.example.movieapp.domain.usecase.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.Movie
import com.example.movieapp.domain.repository.search.SearchMovieRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(
    private val repository: SearchMovieRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Movie>>>{
        return repository.searchMovies(query)
    }
}