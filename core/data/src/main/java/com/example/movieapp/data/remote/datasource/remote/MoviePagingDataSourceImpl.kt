package com.example.movieapp.data.remote.datasource.remote

import androidx.paging.PagingSource
import com.example.movieapp.data.remote.paging.GenreMoviePagingSource
import com.example.movieapp.data.remote.paging.PopularMoviePagingSource
import com.example.movieapp.data.remote.paging.SearchMoviePagingSource
import com.example.movieapp.domain.model.movie.PopularMovie

class MoviePagingDataSourceImpl(
    private val popularMovieRemoteDataSource: PopularMovieRemoteDataSourceImpl,
    private val searchRemoteDataSource: SearchRemoteDataSourceImpl,
) {
    fun getPopularMoviesPagingSource(): PagingSource<Int, PopularMovie> =
        PopularMoviePagingSource(remoteDataSource = popularMovieRemoteDataSource)

    fun searchMoviesPagingSource(query: String): PagingSource<Int, PopularMovie> =
        SearchMoviePagingSource(remoteDataSource = searchRemoteDataSource, query = query)

    fun discoverByGenrePagingSource(genreId: Int): PagingSource<Int, PopularMovie> =
        GenreMoviePagingSource(remoteDataSource = searchRemoteDataSource, genreId = genreId)
}