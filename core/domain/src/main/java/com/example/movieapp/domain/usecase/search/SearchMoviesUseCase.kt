package com.example.movieapp.domain.usecase.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import com.example.movieapp.domain.repository.search.SearchMovieRepository
import com.example.movieapp.domain.usecase.common.BaseUseCase
import com.example.movieapp.domain.usecase.common.withFavouriteState
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(
    private val repository: SearchMovieRepository,
    private val favouriteMovieRepository: FavouriteMovieRepository
) : BaseUseCase<String, List<PopularMovie>>() {
    override fun invoke(params: String): Flow<Resource<List<PopularMovie>>> {
        return repository.searchMovies(query = params).withFavouriteState(favouriteMovieRepository)
    }
}