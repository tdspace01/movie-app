package com.example.movieapp.data.remote.paging

import com.example.movieapp.data.remote.datasource.contract.PopularMovieRemoteDataSource
import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto

class PopularMoviePagingSource(
    private val remoteDataSource: PopularMovieRemoteDataSource
) : GenericMoviePagingResource<PopularMovieResponseDto>(
    fetchPage = { page -> remoteDataSource.getPopularMovies(page) },
    results = {it.results},
    totalPages = {it.totalPages}
)