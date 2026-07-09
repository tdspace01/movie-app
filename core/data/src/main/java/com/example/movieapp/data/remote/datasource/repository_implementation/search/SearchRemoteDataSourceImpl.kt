package com.example.movieapp.data.remote.datasource.repository_implementation.search

import com.example.movieapp.data.remote.datasource.repository.search.SearchRemoteDataSource
import com.example.movieapp.data.remote.model.response_dto.movie.PopularMovieResponseDto
import com.example.movieapp.data.remote.model.response_dto.search.SearchMovieResponseDto
import com.example.movieapp.data.remote.network.search.SearchAndGenreApi
import retrofit2.Response

class SearchRemoteDataSourceImpl(
    private val searchAndGenreApi: SearchAndGenreApi
) : SearchRemoteDataSource {
    override suspend fun searchMovies(query: String, page: Int): Response<SearchMovieResponseDto> {
        return searchAndGenreApi.searchMovies(query = query, page = page)
    }

    override suspend fun discoverMoviesByGenre(genreId: Int, page: Int)
            : Response<PopularMovieResponseDto> {
        return searchAndGenreApi.discoverMoviesByGenre(genreId = genreId, page = page)
    }
}