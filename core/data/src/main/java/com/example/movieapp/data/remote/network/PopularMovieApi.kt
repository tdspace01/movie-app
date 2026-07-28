package com.example.movieapp.data.remote.network

import com.example.movieapp.data.remote.model.response_dto.PopularMovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMovieApi{
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): Response<PopularMovieResponseDto>
}