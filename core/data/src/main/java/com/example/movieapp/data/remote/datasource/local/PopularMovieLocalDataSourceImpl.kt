package com.example.movieapp.data.remote.datasource.local

import com.example.movieapp.data.local.dao.FavouriteMovieDao
import com.example.movieapp.data.local.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

class PopularMovieLocalDataSourceImpl(
    private val favouriteMovieDao: FavouriteMovieDao
) {
    suspend fun insertFavourite(movie: FavouriteMovieEntity) {
        favouriteMovieDao.insertFavourite(movie)
    }

    suspend fun deleteFavourite(movie: FavouriteMovieEntity) {
        favouriteMovieDao.deleteFavourite(movie)
    }

    fun getAllFavourites(): Flow<List<FavouriteMovieEntity>> {
        return favouriteMovieDao.getAllFavourites()
    }

    fun isMovieFavourite(movieId: Int): Flow<Boolean> {
        return favouriteMovieDao.isMovieFavourite(movieId)
    }
}