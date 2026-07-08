package com.example.movieapp.data.remote.datasource.repository.search

import com.example.movieapp.data.remote.model.response_dto.movie.PopularMovieResponseDto
import com.example.movieapp.data.remote.model.response_dto.search.SearchMovieResponseDto
import retrofit2.Response

interface SearchRemoteDataSource {
    suspend fun searchMovies(query: String, page: Int): Response<SearchMovieResponseDto>

    suspend fun discoverMoviesByGenre(genreId: Int, page: Int): Response<PopularMovieResponseDto>
}