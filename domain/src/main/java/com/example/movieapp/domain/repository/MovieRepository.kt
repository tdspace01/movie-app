package com.example.movieapp.domain.repository

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository{
    fun getMovies(): Flow<Resource<List<Movie>>>
}