package com.example.movieapp.domain.repository.movie

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.PopularMovie
import kotlinx.coroutines.flow.Flow

interface PopularMovieRepository{
    fun getMovies(): Flow<Resource<List<PopularMovie>>>

    suspend fun insertFavourite(movie: PopularMovie)

    suspend fun deleteFavourite(movie:PopularMovie)

    fun getAllFavourites(): Flow<List<PopularMovie>>

    fun isMovieFavourite(movieId:Int):Flow<Boolean>
}