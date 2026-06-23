package com.example.movieapp.data.di.datamodule

import com.example.movieapp.data.repository.movie.MovieDetailRepositoryImpl
import com.example.movieapp.data.repository.movie.MovieRepositoryImpl
import com.example.movieapp.data.repository.search.GenreRepositoryImpl
import com.example.movieapp.data.repository.search.SearchMovieRepositoryImpl
import com.example.movieapp.domain.repository.movie.MovieDetailRepository
import com.example.movieapp.domain.repository.movie.MovieRepository
import com.example.movieapp.domain.repository.search.GenreRepository
import com.example.movieapp.domain.repository.search.SearchMovieRepository
import org.koin.dsl.module

val dataModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            popularMovieApi = get(),
            favoriteMovieDao = get()
        )
    }

    single<GenreRepository> {
        GenreRepositoryImpl(searchAndGenreApi = get())
    }

    single<SearchMovieRepository> {
        SearchMovieRepositoryImpl(searchAndGenreApi = get())
    }

    single<MovieDetailRepository> {
        MovieDetailRepositoryImpl(movieDetailRepository = get())
    }
}