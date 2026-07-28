package com.example.movieapp.domain.usecase.common

import androidx.paging.PagingData
import androidx.paging.map
import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun Flow<PagingData<PopularMovie>>.withFavouriteState(
    favouriteIds: Flow<Set<Int>>
): Flow<PagingData<PopularMovie>> = combine(this, favouriteIds) { pagingData, ids ->
    pagingData.map { movie -> movie.copy(isFavorite = movie.id in ids) }
}

fun Flow<NetworkResource<MovieDetail>>.withFavouriteStateForDetail(
    favouriteMovieRepository: FavouriteMovieRepository
): Flow<NetworkResource<MovieDetail>> {
    var lastSuccess: NetworkResource.Success<MovieDetail>? = null

    return combine(this, favouriteMovieRepository.getFavouriteIds())
    { resource, favouriteIds ->
        when (resource) {
            is NetworkResource.Success -> {
                lastSuccess = resource
                NetworkResource.Success(
                    resource.data.copy(isFavorite = resource.data.id in favouriteIds))
            }

            is NetworkResource.Loading -> {
                if (resource.isLoading) {
                    resource
                } else {
                    lastSuccess?.let { success ->
                        NetworkResource.Success(
                            success.data.copy(isFavorite = success.data.id in favouriteIds)
                        )
                    } ?: resource
                }
            }

            is NetworkResource.Error -> resource
        }
    }
}