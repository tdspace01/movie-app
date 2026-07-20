package com.example.movieapp.data.di.dataSourceModule

import com.example.movieapp.data.local.datasource.contract.FavouriteLocalDataSource
import com.example.movieapp.data.local.datasource.implementation.FavouriteLocalDataSourceImpl
import com.example.movieapp.data.remote.datasource.contract.MovieDetailRemoteDataSource
import com.example.movieapp.data.remote.datasource.implementation.MovieDetailRemoteDataSourceImpl
import com.example.movieapp.data.remote.datasource.contract.PopularMovieRemoteDataSource
import com.example.movieapp.data.remote.datasource.implementation.PopularMovieRemoteDataSourceImpl
import com.example.movieapp.data.remote.datasource.contract.SearchAndGenreRemoteDataSource
import com.example.movieapp.data.remote.datasource.implementation.SearchAndGenreRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<MovieDetailRemoteDataSource> {
        MovieDetailRemoteDataSourceImpl(movieDetailApi = get())
    }
    single<FavouriteLocalDataSource> {
        FavouriteLocalDataSourceImpl(favouriteMovieDao = get())
    }
    single<SearchAndGenreRemoteDataSource> {
        SearchAndGenreRemoteDataSourceImpl(searchAndGenreApi = get())
    }
    single<PopularMovieRemoteDataSource> {
        PopularMovieRemoteDataSourceImpl(popularMovieApi = get())
    }
}