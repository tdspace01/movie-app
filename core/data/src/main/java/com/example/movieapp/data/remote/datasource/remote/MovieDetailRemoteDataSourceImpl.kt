package com.example.movieapp.data.remote.datasource.remote

import com.example.movieapp.data.remote.model.movie.MovieDetailResponseDto
import com.example.movieapp.data.remote.network.movie.MovieDetailApi
import retrofit2.Response

class MovieDetailRemoteDataSourceImpl(
    private val movieDetailApi: MovieDetailApi
) {
    suspend fun getMovieDetails(movieId: Int): Response<MovieDetailResponseDto> {
        return movieDetailApi.getMovieDetails(movieId = movieId)
    }
}