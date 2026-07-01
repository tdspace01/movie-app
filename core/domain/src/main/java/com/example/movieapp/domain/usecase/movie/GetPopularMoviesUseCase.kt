package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import com.example.movieapp.domain.repository.movie.PopularMovieRepository
import com.example.movieapp.domain.usecase.common.withFavouriteState
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(
    private val repository: PopularMovieRepository,
    private val favouriteMovieRepository: FavouriteMovieRepository
) {
    operator fun invoke(): Flow<Resource<List<PopularMovie>>> {
        return repository.getMovies().withFavouriteState(favouriteMovieRepository)
    }
}