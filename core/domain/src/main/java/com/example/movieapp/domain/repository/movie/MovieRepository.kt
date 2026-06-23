package com.example.movieapp.domain.repository.movie

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository{
    fun getMovies(): Flow<Resource<List<Movie>>>

    suspend fun insertFavourite(movie: Movie)

    suspend fun deleteFavourite(movie:Movie)

    fun getAllFavourites(): Flow<List<Movie>>

    fun isMovieFavourite(movieId:Int):Flow<Boolean>
}