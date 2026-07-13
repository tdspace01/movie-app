package com.example.movieapp.data.repository.search

import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.datasource.remote.GenreRemoteDataSourceImpl
import com.example.movieapp.data.remote.mapper.MovieMapperConfig
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.domain.repository.search.GenreRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow

class GenreRepositoryImpl(
    private val remoteDataSource: GenreRemoteDataSourceImpl
) : GenreRepository {
    override fun getGenres(): Flow<NetworkResource<List<Genre>>> {
        return apiCall { remoteDataSource.getGenres() }
            .asResource { apiResponse ->
                apiResponse.genres
                    .map { dto -> dto.toDomain() }
                    .also { domainGenres ->
                        MovieMapperConfig.genreMap = domainGenres.associate { it.id to it.name }
                    }
            }
    }
}