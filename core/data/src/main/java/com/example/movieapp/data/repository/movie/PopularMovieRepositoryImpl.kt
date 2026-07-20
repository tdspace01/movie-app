package com.example.movieapp.data.repository.movie

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.datasource.repository.movie.PopularMovieRemoteDataSource
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.PopularMovieRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow

class PopularMovieRepositoryImpl(
    private val remoteDataSource: PopularMovieRemoteDataSource
) : PopularMovieRepository {
    override fun getMovies(): Flow<Resource<List<PopularMovie>>> {
        return apiCall { remoteDataSource.getPopularMovies() }
            .asResource { apiResponse ->
                apiResponse.results.map { dto -> dto.toDomain() }
            }
    }
}