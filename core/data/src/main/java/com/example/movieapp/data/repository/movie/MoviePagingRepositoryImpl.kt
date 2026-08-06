package com.example.movieapp.data.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.remote.datasource.contract.PopularMovieRemoteDataSource
import com.example.movieapp.data.remote.datasource.contract.SearchAndGenreRemoteDataSource
import com.example.movieapp.data.remote.paging.GenreMoviePagingSource
import com.example.movieapp.data.remote.paging.PopularMoviePagingSource
import com.example.movieapp.data.remote.paging.SearchMoviePagingSource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.MoviePagingRepository
import kotlinx.coroutines.flow.Flow

class MoviePagingRepositoryImpl(
    private val popularMovieRemoteDataSource: PopularMovieRemoteDataSource,
    private val searchAndGenreRemoteDataSource: SearchAndGenreRemoteDataSource,
) : MoviePagingRepository {

    override fun getPopularMoviesPaged(): Flow<PagingData<PopularMovie>> =
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            PopularMoviePagingSource(remoteDataSource = popularMovieRemoteDataSource)
        }.flow

    override fun searchMoviesPaged(query: String): Flow<PagingData<PopularMovie>> =
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            SearchMoviePagingSource(
                remoteDataSource = searchAndGenreRemoteDataSource, query = query
            )
        }.flow

    override fun getMoviesByGenrePaged(genreId: Int): Flow<PagingData<PopularMovie>> =
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            GenreMoviePagingSource(
                remoteDataSource = searchAndGenreRemoteDataSource, genreId = genreId
            )
        }.flow
}