package com.example.movieapp.data.remote.datasource.repository_implementation.movie

import androidx.paging.PagingSource
import com.example.movieapp.data.remote.datasource.repository.movie.MoviePagingDataSource
import com.example.movieapp.data.remote.network.movie.PopularMovieApi
import com.example.movieapp.data.remote.network.search.SearchAndGenreApi
import com.example.movieapp.data.remote.paging.GenreMoviePagingSource
import com.example.movieapp.data.remote.paging.PopularMoviePagingSource
import com.example.movieapp.data.remote.paging.SearchMoviePagingSource
import com.example.movieapp.domain.model.movie.PopularMovie

class MoviePagingDataSourceImpl(
    private val popularMovieApi: PopularMovieApi,
    private val searchAndGenreApi: SearchAndGenreApi
) : MoviePagingDataSource {

    override fun getPopularMoviesPagingSource(): PagingSource<Int, PopularMovie> =
        PopularMoviePagingSource(api = popularMovieApi)

    override fun searchMoviesPagingSource(query: String): PagingSource<Int, PopularMovie> =
        SearchMoviePagingSource(api = searchAndGenreApi, query = query)

    override fun discoverByGenrePagingSource(genreId: Int): PagingSource<Int, PopularMovie> =
        GenreMoviePagingSource(api = searchAndGenreApi, genreId = genreId)
}