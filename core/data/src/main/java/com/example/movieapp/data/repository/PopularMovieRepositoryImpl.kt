package com.example.movieapp.data.repository

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.data.remote.network.PopularMovieApi
import com.example.movieapp.domain.model.PopularMovie
import com.example.movieapp.domain.repository.PopularMovieRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow

class PopularMovieRepositoryImpl(
    private val movieApi: PopularMovieApi,
) : PopularMovieRepository{
    override fun getMovies(): Flow<Resource<List<PopularMovie>>> {
        return apiCall { movieApi.getPopularMovies() }
            .asResource { apiResponse ->
                apiResponse.results.map { dto -> dto.toDomain() }
            }
    }
}

//about data sources