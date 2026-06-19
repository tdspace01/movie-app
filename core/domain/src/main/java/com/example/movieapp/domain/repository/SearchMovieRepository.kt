package com.example.movieapp.domain.repository

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.PopularMovie
import kotlinx.coroutines.flow.Flow

interface SearchMovieRepository {
    fun searchMovies(query: String): Flow<Resource<List<PopularMovie>>>
}