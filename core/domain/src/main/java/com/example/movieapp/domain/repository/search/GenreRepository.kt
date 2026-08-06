package com.example.movieapp.domain.repository.search

import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.domain.model.search.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenres(): Flow<NetworkResource<List<Genre>>>
}