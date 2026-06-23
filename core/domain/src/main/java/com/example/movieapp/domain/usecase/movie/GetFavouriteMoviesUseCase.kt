package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.domain.model.movie.Movie
import com.example.movieapp.domain.repository.movie.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>>{
        return repository.getAllFavourites()
    }
}