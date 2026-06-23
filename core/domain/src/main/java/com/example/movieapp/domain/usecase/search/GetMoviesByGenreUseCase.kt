package com.example.movieapp.domain.usecase.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.Movie
import com.example.movieapp.domain.repository.search.GenreRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesByGenreUseCase(
    private val repository: GenreRepository
) {
    operator fun invoke(genreId:Int): Flow<Resource<List<Movie>>>{
        return repository.getMoviesByGenre(genreId)
    }
}
