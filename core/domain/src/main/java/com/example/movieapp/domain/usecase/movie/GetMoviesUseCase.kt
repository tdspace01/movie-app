package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.Movie
import com.example.movieapp.domain.repository.movie.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(
    private val repository: MovieRepository
){
    operator fun invoke(): Flow<Resource<List<Movie>>> {
        return repository.getMovies()
    }
}