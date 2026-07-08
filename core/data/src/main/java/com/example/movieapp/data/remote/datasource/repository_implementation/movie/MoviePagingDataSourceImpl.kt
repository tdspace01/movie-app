package com.example.movieapp.data.remote.datasource.repository_implementation.movie

import androidx.paging.PagingSource
import com.example.movieapp.data.remote.datasource.repository.movie.MoviePagingDataSource
import com.example.movieapp.data.remote.datasource.repository.movie.PopularMovieRemoteDataSource
import com.example.movieapp.data.remote.datasource.repository.search.SearchRemoteDataSource
import com.example.movieapp.data.remote.paging.GenreMoviePagingSource
import com.example.movieapp.data.remote.paging.PopularMoviePagingSource
import com.example.movieapp.data.remote.paging.SearchMoviePagingSource
import com.example.movieapp.domain.model.movie.PopularMovie

class MoviePagingDataSourceImpl(
    private val popularMovieRemoteDataSource: PopularMovieRemoteDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : MoviePagingDataSource {

    override fun getPopularMoviesPagingSource(): PagingSource<Int, PopularMovie> =
        PopularMoviePagingSource(remoteDataSource = popularMovieRemoteDataSource)

    override fun searchMoviesPagingSource(query: String): PagingSource<Int, PopularMovie> =
        SearchMoviePagingSource(remoteDataSource = searchRemoteDataSource, query = query)

    override fun discoverByGenrePagingSource(genreId: Int): PagingSource<Int, PopularMovie> =
        GenreMoviePagingSource(remoteDataSource = searchRemoteDataSource, genreId = genreId)
}