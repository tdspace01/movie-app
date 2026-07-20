package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.domain.repository.movie.MovieDetailRepository
import com.example.movieapp.domain.usecase.common.withDetailFavouriteState
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(
    private val repository: MovieDetailRepository,
    private val getFavouriteIdsUseCase: GetFavouriteIdsUseCase,
) {
    operator fun invoke(movieId: Int): Flow<NetworkResource<MovieDetail>> =
        repository.getMovieDetails(movieId = movieId)
            .withDetailFavouriteState(getFavouriteIdsUseCase())
}