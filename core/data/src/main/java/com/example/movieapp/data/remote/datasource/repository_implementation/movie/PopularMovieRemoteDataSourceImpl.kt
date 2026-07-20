package com.example.movieapp.data.remote.datasource.repository_implementation.movie

import com.example.movieapp.data.remote.datasource.repository.movie.PopularMovieRemoteDataSource
import com.example.movieapp.data.remote.model.response_dto.movie.PopularMovieResponseDto
import com.example.movieapp.data.remote.network.movie.PopularMovieApi
import retrofit2.Response

class PopularMovieRemoteDataSourceImpl(
    private val popularMovieApi: PopularMovieApi
): PopularMovieRemoteDataSource {
    override suspend fun getPopularMovies(): Response<PopularMovieResponseDto> {
        return popularMovieApi.getPopularMovies(page = 1)
    }
}