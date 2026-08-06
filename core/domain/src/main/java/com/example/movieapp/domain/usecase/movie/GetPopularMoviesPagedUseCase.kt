package com.example.movieapp.domain.usecase.movie

import androidx.paging.PagingData
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.MoviePagingRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesPagedUseCase(
    private val repository: MoviePagingRepository,
) {
    operator fun invoke(): Flow<PagingData<PopularMovie>> =
        repository.getPopularMoviesPaged()
}