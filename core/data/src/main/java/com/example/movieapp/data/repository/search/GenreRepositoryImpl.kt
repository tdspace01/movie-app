package com.example.movieapp.data.repository.search

import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.datasource.repository.search.GenreRemoteDataSource
import com.example.movieapp.data.remote.mapper.globalGenreMap
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.domain.repository.search.GenreRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow

class GenreRepositoryImpl(
    private val remoteDataSource: GenreRemoteDataSource
) : GenreRepository {
    override fun getGenres(): Flow<NetworkResource<List<Genre>>> {
        return apiCall { remoteDataSource.getGenres() }
            .asResource { apiResponse ->
                apiResponse.genres
                    .map { dto -> dto.toDomain() }
                    .also { domainGenres ->
                        globalGenreMap = domainGenres.associate { it.id to it.name }
                    }
            }
    }
}