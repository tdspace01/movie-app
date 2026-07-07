package com.example.movieapp.data.di.dataSourceModule

import com.example.movieapp.data.remote.datasource.repository.movie.MovieDetailRemoteDataSource
import com.example.movieapp.data.remote.datasource.repository.movie.MoviePagingDataSource
import com.example.movieapp.data.remote.datasource.repository.movie.PopularMovieLocalDataSource
import com.example.movieapp.data.remote.datasource.repository.search.GenreRemoteDataSource
import com.example.movieapp.data.remote.datasource.repository_implementation.movie.MovieDetailRemoteDataSourceImpl
import com.example.movieapp.data.remote.datasource.repository_implementation.movie.MoviePagingDataSourceImpl
import com.example.movieapp.data.remote.datasource.repository_implementation.movie.PopularMovieLocalDataSourceImpl
import com.example.movieapp.data.remote.datasource.repository_implementation.search.GenreRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<MovieDetailRemoteDataSource> {
        MovieDetailRemoteDataSourceImpl(movieDetailApi = get())
    }
    single<PopularMovieLocalDataSource> {
        PopularMovieLocalDataSourceImpl(favouriteMovieDao = get())
    }
    single<GenreRemoteDataSource> {
        GenreRemoteDataSourceImpl(searchAndGenreApi = get())
    }
    single<MoviePagingDataSource> {
        MoviePagingDataSourceImpl(popularMovieApi = get(), searchAndGenreApi = get())
    }
}