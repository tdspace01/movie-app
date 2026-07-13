package com.example.movieapp.data.di.dataSourceModule

import com.example.movieapp.data.remote.datasource.remote.MovieDetailRemoteDataSourceImpl
import com.example.movieapp.data.remote.datasource.remote.MoviePagingDataSourceImpl
import com.example.movieapp.data.remote.datasource.local.PopularMovieLocalDataSourceImpl
import com.example.movieapp.data.remote.datasource.remote.PopularMovieRemoteDataSourceImpl
import com.example.movieapp.data.remote.datasource.remote.GenreRemoteDataSourceImpl
import com.example.movieapp.data.remote.datasource.remote.SearchRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single {
        MovieDetailRemoteDataSourceImpl(movieDetailApi = get())
    }
    single {
        PopularMovieLocalDataSourceImpl(favouriteMovieDao = get())
    }
    single {
        GenreRemoteDataSourceImpl(searchAndGenreApi = get())
    }
    single {
        PopularMovieRemoteDataSourceImpl(popularMovieApi = get())
    }
    single {
        SearchRemoteDataSourceImpl(searchAndGenreApi = get())
    }
    single {
        MoviePagingDataSourceImpl(
            popularMovieRemoteDataSource = get(),
            searchRemoteDataSource = get(),
        )
    }
}