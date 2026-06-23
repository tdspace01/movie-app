package com.example.movieapp.domain.repository.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface SearchMovieRepository {
    fun searchMovies(query: String): Flow<Resource<List<Movie>>>
}