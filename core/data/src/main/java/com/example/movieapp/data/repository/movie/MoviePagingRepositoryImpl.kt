package com.example.movieapp.data.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.remote.datasource.remote.MoviePagingDataSourceImpl
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.MoviePagingRepository
import kotlinx.coroutines.flow.Flow

class MoviePagingRepositoryImpl(
    private val dataSource: MoviePagingDataSourceImpl
) : MoviePagingRepository {

    override fun getPopularMoviesPaged(): Flow<PagingData<PopularMovie>> =
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            dataSource.getPopularMoviesPagingSource()
        }.flow

    override fun searchMoviesPaged(query: String): Flow<PagingData<PopularMovie>> =
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            dataSource.searchMoviesPagingSource(query = query)
        }.flow

    override fun getMoviesByGenrePaged(genreId: Int): Flow<PagingData<PopularMovie>> =
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            dataSource.discoverByGenrePagingSource(genreId = genreId)
        }.flow
}