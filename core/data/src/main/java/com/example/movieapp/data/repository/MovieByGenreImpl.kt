package com.example.movieapp.data.repository

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.data.remote.network.SearchAndGenreApi
import com.example.movieapp.domain.model.PopularMovie
import com.example.movieapp.domain.repository.MoviesByGenreRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow

class MovieByGenreImpl(
    private val searchAndGenreApi: SearchAndGenreApi
): MoviesByGenreRepository {
    override fun getMoviesByGenre(genreId: Int): Flow<Resource<List<PopularMovie>>> {
        return apiCall { searchAndGenreApi.discoverByGenre(genreId) }
            .asResource { apiResource ->
                apiResource.results.map { dto->
                    dto.toDomain() }
            }
    }
}