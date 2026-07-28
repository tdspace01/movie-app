package com.example.movieapp.data.di.localmodule

import androidx.room.Room
import com.example.movieapp.data.local.roomdb.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movie_app.db"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()
    }

    single { get<AppDatabase>().favouriteMovieDao }

}