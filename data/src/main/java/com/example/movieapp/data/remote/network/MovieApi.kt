package com.example.movieapp.data.remote.network

import com.example.movieapp.data.remote.model.response_dto.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi{
    @GET("getting/movie")
    suspend fun getPopularMovies(
        @Query("api_key")apiKey: String = "my_api_key"
    ): Response<MovieResponseDto>
}