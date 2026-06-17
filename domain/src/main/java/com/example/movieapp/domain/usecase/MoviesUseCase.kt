package com.example.movieapp.domain.usecase

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MoviesUseCase(
    private val repository: MovieRepository
){
    operator fun invoke(): Flow<Resource<List<Movie>>>{
        return repository.getMovies()
    }
}