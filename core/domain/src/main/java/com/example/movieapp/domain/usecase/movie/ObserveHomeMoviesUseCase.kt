package com.example.movieapp.domain.usecase.movie

import androidx.paging.PagingData
import com.example.movieapp.domain.model.movie.HomeMovieFilter
import com.example.movieapp.domain.model.movie.MovieListSource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.model.movie.resolve
import com.example.movieapp.domain.usecase.search.GetMoviesByGenrePagedUseCase
import com.example.movieapp.domain.usecase.search.SearchMoviesPagedUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan

class ObserveHomeMoviesUseCase(
    private val getPopularMoviesPagedUseCase: GetPopularMoviesPagedUseCase,
    private val searchMoviesPagedUseCase: SearchMoviesPagedUseCase,
    private val getMoviesByGenrePagedUseCase: GetMoviesByGenrePagedUseCase,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        filter: Flow<HomeMovieFilter>,
        refreshTrigger: Flow<Unit>,
    ): Flow<PagingData<PopularMovie>> {
        val refreshGenerations = refreshTrigger
            .scan(0) { generation, _ -> generation + 1 }
            .onStart { emit(0) }

        return combine(filter, refreshGenerations) { f, generation ->
            HomeMoviesRequest(filter = f, generation = generation)
        }
            .distinctUntilChanged()
            .flatMapLatest { request ->
                when (val source = request.filter.resolve()) {
                    is MovieListSource.Popular -> getPopularMoviesPagedUseCase()
                    is MovieListSource.Search -> searchMoviesPagedUseCase(source.query)
                    is MovieListSource.ByGenre -> getMoviesByGenrePagedUseCase(source.genreId)
                }
            }
    }

    private data class HomeMoviesRequest(
        val filter: HomeMovieFilter,
        val generation: Int,
    )
}