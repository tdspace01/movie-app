package com.example.movieapp.domain.usecase

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.PopularMovie
import com.example.movieapp.domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow

class MoviesByGenreUseCase(
    private val repository: GenreRepository
) {
    operator fun invoke(genreId:Int): Flow<Resource<List<PopularMovie>>>{
        return repository.getMoviesByGenre(genreId)
    }
}
