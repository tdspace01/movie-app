package com.example.movieapp.domain.usecase.search

import androidx.paging.PagingData
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.MoviePagingRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesByGenrePagedUseCase(
    private val repository: MoviePagingRepository,
) {
    operator fun invoke(genreId: Int): Flow<PagingData<PopularMovie>> =
        repository.getMoviesByGenrePaged(genreId = genreId)
}