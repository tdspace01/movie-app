package com.example.movieapp.data.repository_implementation

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.data.remote.network.MovieApi
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.network.response_handler.ResponseHandler
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val responseHandler: ResponseHandler
) : MovieRepository{
    override fun getMovies(): Flow<Resource<List<Movie>>> {
        return responseHandler
            .safeApiCall { movieApi.getPopularMovies() }
            .asResource { apiResponse ->
                apiResponse.results.map { dto -> dto.toDomain() }
            }
    }
}