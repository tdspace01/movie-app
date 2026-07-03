package com.example.movieapp.data.di.datamodule

import com.example.movieapp.data.repository.movie.FavouriteMovieRepositoryImpl
import com.example.movieapp.data.repository.movie.MovieDetailRepositoryImpl
import com.example.movieapp.data.repository.movie.PopularMovieRepositoryImpl
import com.example.movieapp.data.repository.search.GenreRepositoryImpl
import com.example.movieapp.data.repository.search.SearchMovieRepositoryImpl
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import com.example.movieapp.domain.repository.movie.MovieDetailRepository
import com.example.movieapp.domain.repository.movie.PopularMovieRepository
import com.example.movieapp.domain.repository.search.GenreRepository
import com.example.movieapp.domain.repository.search.SearchMovieRepository
import org.koin.dsl.module

val dataModule = module {
    single<PopularMovieRepository> {
        PopularMovieRepositoryImpl(remoteDataSource = get())
    }

    single<FavouriteMovieRepository> {
        FavouriteMovieRepositoryImpl(localDataSource = get())
    }

    single<GenreRepository> {
        GenreRepositoryImpl(remoteDataSource = get())
    }

    single<SearchMovieRepository> {
        SearchMovieRepositoryImpl(remoteDataSource = get())
    }

    single<MovieDetailRepository> {
        MovieDetailRepositoryImpl(remoteDataSource = get())
    }
}