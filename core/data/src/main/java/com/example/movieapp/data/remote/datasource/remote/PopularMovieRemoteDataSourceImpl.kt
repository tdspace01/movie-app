package com.example.movieapp.data.remote.datasource.remote

import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto
import com.example.movieapp.data.remote.network.movie.PopularMovieApi
import retrofit2.Response

class PopularMovieRemoteDataSourceImpl(
    private val popularMovieApi: PopularMovieApi
) {
    suspend fun getPopularMovies(page: Int): Response<PopularMovieResponseDto> {
        return popularMovieApi.getPopularMovies(page = page)
    }
}