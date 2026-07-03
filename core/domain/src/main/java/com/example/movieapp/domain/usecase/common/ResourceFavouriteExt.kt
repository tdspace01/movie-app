package com.example.movieapp.domain.usecase.common

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun Flow<Resource<List<PopularMovie>>>.withFavouriteState(
    favouriteMovieRepository: FavouriteMovieRepository
): Flow<Resource<List<PopularMovie>>> {
    var lastSuccess: Resource.Success<List<PopularMovie>>? = null

    return combine(this, favouriteMovieRepository.getFavouriteIds()) { resource, favouriteIds ->
        when (resource) {
            is Resource.Success -> {
                lastSuccess = resource
                Resource.Success(resource.data.map { it.copy(isFavorite = it.id in favouriteIds) })
            }

            is Resource.Loading -> {
                if (resource.isLoading) {
                    resource
                } else {
                    lastSuccess?.let { success ->
                        Resource.Success(success.data.map { it.copy(isFavorite = it.id in favouriteIds) })
                    } ?: resource
                }
            }

            is Resource.Error -> resource
        }
    }
}

fun Flow<Resource<MovieDetail>>.withFavouriteStateForDetail(
    favouriteMovieRepository: FavouriteMovieRepository
): Flow<Resource<MovieDetail>> {
    var lastSuccess: Resource.Success<MovieDetail>? = null

    return combine(this, favouriteMovieRepository.getFavouriteIds()) { resource, favouriteIds ->
        when (resource) {
            is Resource.Success -> {
                lastSuccess = resource
                Resource.Success(resource.data.copy(isFavorite = resource.data.id in favouriteIds))
            }

            is Resource.Loading -> {
                if (resource.isLoading) {
                    resource
                } else {
                    lastSuccess?.let { success ->
                        Resource.Success(success.data.copy(isFavorite = success.data.id in favouriteIds))
                    } ?: resource
                }
            }

            is Resource.Error -> resource
        }
    }
}