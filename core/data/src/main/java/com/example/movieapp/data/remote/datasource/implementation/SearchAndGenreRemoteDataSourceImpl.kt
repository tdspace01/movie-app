package com.example.movieapp.data.remote.datasource.implementation

import com.example.movieapp.data.remote.datasource.contract.SearchAndGenreRemoteDataSource
import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto
import com.example.movieapp.data.remote.model.search.GenreListResponseDto
import com.example.movieapp.data.remote.model.search.SearchMovieResponseDto
import com.example.movieapp.data.remote.network.search.SearchAndGenreApi
import retrofit2.Response

class SearchAndGenreRemoteDataSourceImpl(
    private val searchAndGenreApi: SearchAndGenreApi
) : SearchAndGenreRemoteDataSource {
    override suspend fun getGenres(): Response<GenreListResponseDto> {
        return searchAndGenreApi.getGenres()
    }

    override suspend fun searchMovies(query: String, page: Int): Response<SearchMovieResponseDto> {
        return searchAndGenreApi.searchMovies(query = query, page = page)
    }

    override suspend fun discoverMoviesByGenre(genreId: Int, page: Int):
            Response<PopularMovieResponseDto> {
        return searchAndGenreApi.discoverMoviesByGenre(genreId = genreId, page = page)
    }
}