package com.example.movieapp.data.remote.datasource.repository_implementation.search

import com.example.movieapp.data.remote.datasource.repository.search.SearchMovieRemoteDataSource
import com.example.movieapp.data.remote.model.response_dto.search.SearchMovieResponseDto
import com.example.movieapp.data.remote.network.search.SearchAndGenreApi
import retrofit2.Response

class SearchMovieRemoteDataSourceImpl(
    private val searchAndGenreApi: SearchAndGenreApi
): SearchMovieRemoteDataSource {
    override suspend fun searchMovies(query: String): Response<SearchMovieResponseDto> {
        return searchAndGenreApi.searchMovies(query = query)
    }
}