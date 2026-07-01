package com.example.movieapp.domain.usecase.common

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.map
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun Flow<Resource<List<PopularMovie>>>.withFavouriteState(
    favouriteMovieRepository: FavouriteMovieRepository
): Flow<Resource<List<PopularMovie>>> {
    return combine(this, favouriteMovieRepository.getFavouriteIds()) { resource, favouriteIds ->
        resource.map { movies ->
            movies.map { movie -> movie.copy(isFavorite = movie.id in favouriteIds) }
        }
    }
}