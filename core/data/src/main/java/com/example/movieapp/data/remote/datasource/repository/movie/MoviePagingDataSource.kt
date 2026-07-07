package com.example.movieapp.data.remote.datasource.repository.movie

import androidx.paging.PagingSource
import com.example.movieapp.domain.model.movie.PopularMovie

interface MoviePagingDataSource {
    fun getPopularMoviesPagingSource(): PagingSource<Int, PopularMovie>
    fun searchMoviesPagingSource(query: String): PagingSource<Int, PopularMovie>
    fun discoverByGenrePagingSource(genreId: Int): PagingSource<Int, PopularMovie>
}