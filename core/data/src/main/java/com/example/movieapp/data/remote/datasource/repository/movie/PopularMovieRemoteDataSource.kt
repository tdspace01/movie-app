package com.example.movieapp.data.remote.datasource.repository.movie

import com.example.movieapp.data.remote.model.response_dto.movie.PopularMovieResponseDto
import retrofit2.Response

interface PopularMovieRemoteDataSource {
    suspend fun getPopularMovies(page: Int): Response<PopularMovieResponseDto>
}