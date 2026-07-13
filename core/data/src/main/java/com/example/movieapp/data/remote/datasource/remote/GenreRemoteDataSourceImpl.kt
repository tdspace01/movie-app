package com.example.movieapp.data.remote.datasource.remote

import com.example.movieapp.data.remote.model.search.GenreListResponseDto
import com.example.movieapp.data.remote.network.search.SearchAndGenreApi
import retrofit2.Response

class GenreRemoteDataSourceImpl(
    private val searchAndGenreApi: SearchAndGenreApi
) {
    suspend fun getGenres(): Response<GenreListResponseDto> {
        return searchAndGenreApi.getGenres()
    }
}