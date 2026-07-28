package com.example.movieapp.domain.usecase.search

import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteIdsUseCase(
    private val repository: FavouriteMovieRepository
) {
    operator fun invoke(): Flow<Set<Int>> = repository.getFavouriteIds()
}