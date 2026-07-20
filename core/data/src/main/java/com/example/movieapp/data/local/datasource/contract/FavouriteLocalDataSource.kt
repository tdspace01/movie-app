package com.example.movieapp.data.local.datasource.contract

import com.example.movieapp.data.local.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface FavouriteLocalDataSource {
    suspend fun insertFavourite(movie: FavouriteMovieEntity)
    suspend fun deleteFavourite(movie: FavouriteMovieEntity)
    fun getAllFavourites(): Flow<List<FavouriteMovieEntity>>
    fun isMovieFavourite(movieId: Int): Flow<Boolean>
}