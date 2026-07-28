package com.example.movieapp.data.remote.datasource.repository.search

import com.example.movieapp.data.remote.model.response_dto.search.GenreListResponseDto
import retrofit2.Response

interface GenreRemoteDataSource {
    suspend fun getGenres(): Response<GenreListResponseDto>
}