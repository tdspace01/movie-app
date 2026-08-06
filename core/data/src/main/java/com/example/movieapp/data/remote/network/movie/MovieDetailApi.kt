package com.example.movieapp.data.remote.network.movie

import com.example.movieapp.data.remote.model.movie.MovieDetailResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailApi {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Response<MovieDetailResponseDto>
}