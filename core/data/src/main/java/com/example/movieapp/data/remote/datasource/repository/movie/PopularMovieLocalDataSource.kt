package com.example.movieapp.data.remote.datasource.repository.movie

import com.example.movieapp.data.local.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface PopularMovieLocalDataSource {
    suspend fun insertFavourite(movie: FavouriteMovieEntity)
    suspend fun deleteFavourite(movie: FavouriteMovieEntity)
    fun getAllFavourites(): Flow<List<FavouriteMovieEntity>>
    fun isMovieFavourite(movieId:Int): Flow<Boolean>
}