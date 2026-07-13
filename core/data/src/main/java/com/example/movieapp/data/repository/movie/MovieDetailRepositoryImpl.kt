package com.example.movieapp.data.repository.movie

import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.datasource.remote.MovieDetailRemoteDataSourceImpl
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.domain.repository.movie.MovieDetailRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow

class MovieDetailRepositoryImpl(
    private val remoteDataSource: MovieDetailRemoteDataSourceImpl
) : MovieDetailRepository {
    override fun getMovieDetails(movieId: Int): Flow<NetworkResource<MovieDetail>> {
        return apiCall { remoteDataSource.getMovieDetails(movieId = movieId) }
            .asResource { apiResponse ->
                apiResponse.toDomain()
            }
    }
}