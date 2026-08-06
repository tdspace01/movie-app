package com.example.movieapp.data.remote.datasource.contract

import com.example.movieapp.data.remote.model.movie.MovieDetailResponseDto
import retrofit2.Response

interface MovieDetailRemoteDataSource {
    suspend fun getMovieDetails(movieId: Int): Response<MovieDetailResponseDto>
}