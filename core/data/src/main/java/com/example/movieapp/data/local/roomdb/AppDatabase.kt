package com.example.movieapp.data.local.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movieapp.data.local.dao.FavouriteMovieDao
import com.example.movieapp.data.local.entity.FavouriteMovieEntity

@Database(
    entities = [FavouriteMovieEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val favouriteMovieDao: FavouriteMovieDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE favourite_movies ADD COLUMN addedAt INTEGER NOT NULL DEFAULT 0"
                )
            }
        }
    }
}