package com.example.movieapp.data.remote.datasource.repository.movie

import com.example.movieapp.data.remote.model.response_dto.movie.MovieDetailResponseDto
import retrofit2.Response

interface MovieDetailRemoteDataSource {
    suspend fun getMovieDetails(movieId:Int): Response<MovieDetailResponseDto>
}