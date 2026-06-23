package com.example.movieapp.data.local.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.local.dao.FavouriteMovieDao
import com.example.movieapp.data.local.entity.FavouriteMovieEntity

@Database(
    entities = [FavouriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase(){
    abstract val favouriteMovieDao: FavouriteMovieDao
}