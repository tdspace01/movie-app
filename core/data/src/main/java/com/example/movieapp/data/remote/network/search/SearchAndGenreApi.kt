package com.example.movieapp.data.remote.network.search

import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto
import com.example.movieapp.data.remote.model.search.GenreListResponseDto
import com.example.movieapp.data.remote.model.search.SearchMovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAndGenreApi {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): Response<SearchMovieResponseDto>

    @GET("discover/movie")
    suspend fun discoverMoviesByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): Response<PopularMovieResponseDto>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenreListResponseDto>
}