package com.example.movieapp.data.repository.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.datasource.repository.search.SearchMovieRemoteDataSource
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.search.SearchMovieRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow

class SearchMovieRepositoryImpl(
    private val remoteDataSource: SearchMovieRemoteDataSource
) : SearchMovieRepository {
    override fun searchMovies(query: String): Flow<Resource<List<PopularMovie>>> {
        return apiCall { remoteDataSource.searchMovies(query = query) }
            .asResource { apiResponse ->
                apiResponse.results.map { dto -> dto.toDomain() }
            }
    }
}