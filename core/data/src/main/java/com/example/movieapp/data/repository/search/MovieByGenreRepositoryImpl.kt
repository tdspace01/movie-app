package com.example.movieapp.data.repository.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.datasource.repository.search.MovieByGenreRemoteDataSource
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.search.MoviesByGenreRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow

class MovieByGenreRepositoryImpl(
    private val remoteDataSource: MovieByGenreRemoteDataSource
) : MoviesByGenreRepository {
    override fun getMoviesByGenre(genreId: Int): Flow<Resource<List<PopularMovie>>> {
        return apiCall { remoteDataSource.getMoviesByGenre(genreId) }
            .asResource { apiResource ->
                apiResource.results.map { dto -> dto.toDomain() }
            }
    }
}