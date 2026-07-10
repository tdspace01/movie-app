package com.example.movieapp.domain.usecase.search

import androidx.paging.PagingData
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.MoviePagingRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesPagedUseCase(
    private val repository: MoviePagingRepository,
) {
    operator fun invoke(query: String): Flow<PagingData<PopularMovie>> =
        repository.searchMoviesPaged(query = query)
}