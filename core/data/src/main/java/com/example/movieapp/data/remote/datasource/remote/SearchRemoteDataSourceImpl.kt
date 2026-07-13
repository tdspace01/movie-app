package com.example.movieapp.data.remote.datasource.remote

import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto
import com.example.movieapp.data.remote.model.search.SearchMovieResponseDto
import com.example.movieapp.data.remote.network.search.SearchAndGenreApi
import retrofit2.Response

class SearchRemoteDataSourceImpl(
    private val searchAndGenreApi: SearchAndGenreApi
) {
    suspend fun searchMovies(query: String, page: Int): Response<SearchMovieResponseDto> {
        return searchAndGenreApi.searchMovies(query = query, page = page)
    }

    suspend fun discoverMoviesByGenre(genreId: Int, page: Int): Response<PopularMovieResponseDto> {
        return searchAndGenreApi.discoverMoviesByGenre(genreId = genreId, page = page)
    }
}