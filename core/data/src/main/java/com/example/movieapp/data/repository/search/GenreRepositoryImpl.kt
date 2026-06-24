package com.example.movieapp.data.repository.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.mapper.globalGenreMap
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.data.remote.network.search.SearchAndGenreApi
import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.domain.repository.search.GenreRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow

class GenreRepositoryImpl(
    private val searchAndGenreApi: SearchAndGenreApi
) : GenreRepository {
    override fun getGenres(): Flow<Resource<List<Genre>>> {
        return apiCall { searchAndGenreApi.getGenres() }
            .asResource { apiResponse ->
                apiResponse.genres
                    .map { dto -> dto.toDomain() }
                    .also { domainGenres ->
                        globalGenreMap = domainGenres.associate { it.id to it.name }
                    }
            }
    }
}