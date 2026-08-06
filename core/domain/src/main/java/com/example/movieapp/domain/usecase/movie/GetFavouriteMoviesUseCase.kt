package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteMoviesUseCase(
    private val repository: FavouriteMovieRepository
) {
    operator fun invoke(): Flow<List<PopularMovie>> {
        return repository.getAllFavourites()
    }
}