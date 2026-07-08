package com.example.movieapp.domain.usecase.search

import androidx.paging.PagingData
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.MoviePagingRepository
import com.example.movieapp.domain.usecase.common.withFavouriteState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class GetMoviesByGenrePagedUseCase(
    private val repository: MoviePagingRepository,
    private val getFavouriteIdsUseCase: GetFavouriteIdsUseCase,
) {
    operator fun invoke(genreId: Int, scope: CoroutineScope): Flow<PagingData<PopularMovie>> =
        repository.getMoviesByGenrePaged(genreId = genreId)
            .withFavouriteState(getFavouriteIdsUseCase(), scope)
}