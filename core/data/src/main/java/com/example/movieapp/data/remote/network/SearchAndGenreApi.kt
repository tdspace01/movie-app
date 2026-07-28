package com.example.movieapp.data.remote.network

import com.example.movieapp.data.remote.model.response_dto.GenreListResponseDto
import com.example.movieapp.data.remote.model.response_dto.PopularMovieResponseDto
import com.example.movieapp.data.remote.model.response_dto.SearchMovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAndGenreApi {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): Response<SearchMovieResponseDto>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenreListResponseDto>

    @GET("discover/movie")
    suspend fun discoverByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1,
    ): Response<PopularMovieResponseDto>
}