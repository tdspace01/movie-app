package com.example.movieapp.data.di.datamodule

import com.example.movieapp.data.repository.movie.FavouriteMovieRepositoryImpl
import com.example.movieapp.data.repository.movie.MovieDetailRepositoryImpl
import com.example.movieapp.data.repository.movie.MoviePagingRepositoryImpl
import com.example.movieapp.data.repository.search.GenreRepositoryImpl
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import com.example.movieapp.domain.repository.movie.MovieDetailRepository
import com.example.movieapp.domain.repository.movie.MoviePagingRepository
import com.example.movieapp.domain.repository.search.GenreRepository
import org.koin.dsl.module

val dataModule = module {
    single<FavouriteMovieRepository> {
        FavouriteMovieRepositoryImpl(localDataSource = get())
    }

    single<GenreRepository> {
        GenreRepositoryImpl(remoteDataSource = get())
    }

    single<MovieDetailRepository> {
        MovieDetailRepositoryImpl(remoteDataSource = get())
    }

    single<MoviePagingRepository> {
        MoviePagingRepositoryImpl(dataSource = get())
    }
}