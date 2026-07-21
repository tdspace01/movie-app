package com.example.movieapp.data.repository

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.data.remote.network.SearchAndGenreApi
import com.example.movieapp.domain.model.PopularMovie
import com.example.movieapp.domain.repository.SearchMovieRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow

class SearchMovieRepositoryImpl(
    private val searchAndGenreApi: SearchAndGenreApi
) : SearchMovieRepository {
    override fun searchMovies(query: String): Flow<Resource<List<PopularMovie>>> {
        return apiCall { searchAndGenreApi.searchMovies(query) }
            .asResource { apiResponse->
                apiResponse.results.map { dto->
                    dto.toDomain()
                }
            }
    }
}