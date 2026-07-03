package com.example.movieapp.domain.usecase.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import com.example.movieapp.domain.repository.movie.PopularMovieRepository
import com.example.movieapp.domain.usecase.common.withFavouriteState
import kotlinx.coroutines.flow.Flow

class GetMoviesByGenreUseCase(
    private val repository: PopularMovieRepository,
    private val favouriteMovieRepository: FavouriteMovieRepository
) {
    operator fun invoke(genreName: String): Flow<Resource<List<PopularMovie>>> {
        return repository.getMovies()
            .asResource { movies ->
                movies.filter { it.category.equals(genreName, ignoreCase = true) }
            }
            .withFavouriteState(favouriteMovieRepository)
    }
}