package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import com.example.movieapp.domain.repository.movie.MovieDetailRepository
import com.example.movieapp.domain.usecase.common.withFavouriteStateForDetail
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(
    private val repository: MovieDetailRepository,
    private val favouriteMovieRepository: FavouriteMovieRepository
) {
    operator fun invoke(movieId: Int): Flow<NetworkResource<MovieDetail>> {
        return repository.getMovieDetails(movieId = movieId)
            .withFavouriteStateForDetail(favouriteMovieRepository)
    }
}