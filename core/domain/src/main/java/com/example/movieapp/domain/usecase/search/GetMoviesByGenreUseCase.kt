package com.example.movieapp.domain.usecase.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import com.example.movieapp.domain.repository.search.MoviesByGenreRepository
import com.example.movieapp.domain.usecase.common.BaseUseCase
import com.example.movieapp.domain.usecase.common.withFavouriteState
import kotlinx.coroutines.flow.Flow

class GetMoviesByGenreUseCase(
    private val repository: MoviesByGenreRepository,
    private val favouriteMovieRepository: FavouriteMovieRepository
) : BaseUseCase<Int, List<PopularMovie>>() {
    override fun invoke(params: Int): Flow<Resource<List<PopularMovie>>> {
        return repository.getMoviesByGenre(genreId = params).withFavouriteState(favouriteMovieRepository)
    }
}