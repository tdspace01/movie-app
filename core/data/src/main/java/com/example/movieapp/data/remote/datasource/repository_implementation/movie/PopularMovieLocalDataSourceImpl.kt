package com.example.movieapp.data.remote.datasource.repository_implementation.movie

import com.example.movieapp.data.local.dao.FavouriteMovieDao
import com.example.movieapp.data.local.entity.FavouriteMovieEntity
import com.example.movieapp.data.remote.datasource.repository.movie.PopularMovieLocalDataSource
import kotlinx.coroutines.flow.Flow

class PopularMovieLocalDataSourceImpl(
    private val favouriteMovieDao: FavouriteMovieDao
): PopularMovieLocalDataSource {
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