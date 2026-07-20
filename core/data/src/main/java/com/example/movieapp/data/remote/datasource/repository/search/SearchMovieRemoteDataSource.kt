package com.example.movieapp.data.remote.datasource.repository.search

import com.example.movieapp.data.remote.model.response_dto.search.SearchMovieResponseDto
import retrofit2.Response

interface SearchMovieRemoteDataSource {
    suspend fun searchMovies(query: String): Response<SearchMovieResponseDto>
}