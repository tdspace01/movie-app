package com.example.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.local.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(movie: FavouriteMovieEntity)

    @Delete
    suspend fun deleteFavourite(movie: FavouriteMovieEntity)

    @Query("SELECT * FROM favourite_movies ORDER BY addedAt DESC")
    fun getAllFavourites(): Flow<List<FavouriteMovieEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_movies Where id = :movieId)")
    fun isMovieFavourite(movieId: Int): Flow<Boolean>
}