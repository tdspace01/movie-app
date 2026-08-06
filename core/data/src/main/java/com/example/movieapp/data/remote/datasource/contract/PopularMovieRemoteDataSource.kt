package com.example.movieapp.data.remote.datasource.contract

import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto
import retrofit2.Response

interface PopularMovieRemoteDataSource {
    suspend fun getPopularMovies(page: Int): Response<PopularMovieResponseDto>
}