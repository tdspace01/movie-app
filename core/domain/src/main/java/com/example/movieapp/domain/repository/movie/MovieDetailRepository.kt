package com.example.movieapp.domain.repository.movie

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun getMovieDetails(movieId:Int): Flow<Resource<MovieDetail>>
}