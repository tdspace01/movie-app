package com.example.movieapp.data.remote.network.movie

import com.example.movieapp.data.remote.model.response_dto.movie.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi{
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): Response<MovieResponseDto>
}