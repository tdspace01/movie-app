package com.example.movieapp.data.remote.datasource.repository.search

import com.example.movieapp.data.remote.model.response_dto.movie.PopularMovieResponseDto
import retrofit2.Response

interface MovieByGenreRemoteDataSource {
    suspend fun getMoviesByGenre(genreId:Int): Response<PopularMovieResponseDto>
}