package com.example.movieapp.data.remote.datasource.repository_implementation.search

import com.example.movieapp.data.remote.datasource.repository.search.GenreRemoteDataSource
import com.example.movieapp.data.remote.model.response_dto.search.GenreListResponseDto
import com.example.movieapp.data.remote.network.search.SearchAndGenreApi
import retrofit2.Response

class GenreRemoteDataSourceImpl(
    private val searchAndGenreApi: SearchAndGenreApi
) : GenreRemoteDataSource {
    override suspend fun getGenres(): Response<GenreListResponseDto> {
        return searchAndGenreApi.getGenres()
    }
}