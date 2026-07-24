package com.example.movieapp.data.remote.paging

import com.example.movieapp.data.remote.datasource.contract.SearchAndGenreRemoteDataSource
import com.example.movieapp.data.remote.model.search.SearchMovieResponseDto

class SearchMoviePagingSource(
    private val remoteDataSource: SearchAndGenreRemoteDataSource,
    private val query: String
) : GenericMoviePagingResource<SearchMovieResponseDto>(
    fetchPage = { page -> remoteDataSource.searchMovies(query,page) },
    results = {it.results},
    totalPages = {it.totalPages}
)