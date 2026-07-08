package com.example.movieapp.domain.usecase.movie

import androidx.paging.PagingData
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.MoviePagingRepository
import com.example.movieapp.domain.usecase.common.withFavouriteState
import com.example.movieapp.domain.usecase.search.GetFavouriteIdsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesPagedUseCase(
    private val repository: MoviePagingRepository,
    private val getFavouriteIdsUseCase: GetFavouriteIdsUseCase,
) {
    operator fun invoke(scope: CoroutineScope): Flow<PagingData<PopularMovie>> =
        repository.getPopularMoviesPaged()
            .withFavouriteState(getFavouriteIdsUseCase(), scope)
}