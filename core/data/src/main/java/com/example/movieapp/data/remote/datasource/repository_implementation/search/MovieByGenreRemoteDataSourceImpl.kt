package com.example.movieapp.data.remote.datasource.repository_implementation.search

import com.example.movieapp.data.remote.datasource.repository.search.MovieByGenreRemoteDataSource
import com.example.movieapp.data.remote.model.response_dto.movie.PopularMovieResponseDto
import com.example.movieapp.data.remote.network.search.SearchAndGenreApi
import retrofit2.Response

class MovieByGenreRemoteDataSourceImpl(
    private val searchAndGenreApi: SearchAndGenreApi
): MovieByGenreRemoteDataSource {
    override suspend fun getMoviesByGenre(genreId: Int): Response<PopularMovieResponseDto> {
        return searchAndGenreApi.discoverByGenre(genreId)
    }
}