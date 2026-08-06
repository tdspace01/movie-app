package com.example.movieapp.data.remote.paging

import com.example.movieapp.data.remote.datasource.contract.SearchAndGenreRemoteDataSource
import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto

class GenreMoviePagingSource(
    private val remoteDataSource: SearchAndGenreRemoteDataSource,
    private val genreId: Int
) : GenericMoviePagingResource<PopularMovieResponseDto>(
    fetchPage = { page -> remoteDataSource.discoverMoviesByGenre(genreId,page) },
    results = {it.results},
    totalPages = {it.totalPages}
)