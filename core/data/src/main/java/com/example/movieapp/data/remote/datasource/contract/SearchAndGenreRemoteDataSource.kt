package com.example.movieapp.data.remote.datasource.contract

import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto
import com.example.movieapp.data.remote.model.search.GenreListResponseDto
import com.example.movieapp.data.remote.model.search.SearchMovieResponseDto
import retrofit2.Response

interface SearchAndGenreRemoteDataSource {
    suspend fun getGenres(): Response<GenreListResponseDto>
    suspend fun searchMovies(query: String, page: Int): Response<SearchMovieResponseDto>
    suspend fun discoverMoviesByGenre(genreId: Int, page: Int): Response<PopularMovieResponseDto>
}