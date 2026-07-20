package com.example.movieapp.data.remote.datasource.repository_implementation.movie

import com.example.movieapp.data.remote.datasource.repository.movie.MovieDetailRemoteDataSource
import com.example.movieapp.data.remote.model.response_dto.movie.MovieDetailResponseDto
import com.example.movieapp.data.remote.network.movie.MovieDetailApi
import retrofit2.Response

class MovieDetailRemoteDataSourceImpl(
    private val movieDetailApi: MovieDetailApi
) : MovieDetailRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Int): Response<MovieDetailResponseDto> {
        return movieDetailApi.getMovieDetails(movieId = movieId)
    }
}