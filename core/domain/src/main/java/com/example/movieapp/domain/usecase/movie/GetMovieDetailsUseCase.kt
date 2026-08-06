package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.domain.repository.movie.MovieDetailRepository
import com.example.movieapp.domain.usecase.common.withDetailFavouriteState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan

class GetMovieDetailsUseCase(
    private val repository: MovieDetailRepository,
    private val getFavouriteIdsUseCase: GetFavouriteIdsUseCase,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        movieId: Int,
        refreshTrigger: Flow<Unit>,
    ): Flow<NetworkResource<MovieDetail>> {
        val refreshGenerations = refreshTrigger
            .scan(0) { generation, _ -> generation + 1 }
            .onStart { emit(0) }

        return refreshGenerations.flatMapLatest {
            repository.getMovieDetails(movieId = movieId)
                .withDetailFavouriteState(getFavouriteIdsUseCase())
        }
    }
}