package com.example.movieapp.data.local.datasource.implementation

import com.example.movieapp.data.local.dao.FavouriteMovieDao
import com.example.movieapp.data.local.datasource.contract.FavouriteLocalDataSource
import com.example.movieapp.data.local.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

class FavouriteLocalDataSourceImpl(
    private val favouriteMovieDao: FavouriteMovieDao
) : FavouriteLocalDataSource {
    override suspend fun insertFavourite(movie: FavouriteMovieEntity) {
        favouriteMovieDao.insertFavourite(movie)
    }

    override suspend fun deleteFavourite(movie: FavouriteMovieEntity) {
        favouriteMovieDao.deleteFavourite(movie)
    }

    override fun getAllFavourites(): Flow<List<FavouriteMovieEntity>> {
        return favouriteMovieDao.getAllFavourites()
    }

    override fun isMovieFavourite(movieId: Int): Flow<Boolean> {
        return favouriteMovieDao.isMovieFavourite(movieId)
    }
}