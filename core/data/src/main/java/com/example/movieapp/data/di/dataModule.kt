package com.example.movieapp.data.di

import com.example.movieapp.data.repository.GenreRepositoryImpl
import com.example.movieapp.data.repository.PopularMovieRepositoryImpl
import com.example.movieapp.data.repository.SearchMovieRepositoryImpl
import com.example.movieapp.domain.repository.GenreRepository
import com.example.movieapp.domain.repository.PopularMovieRepository
import com.example.movieapp.domain.repository.SearchMovieRepository
import org.koin.dsl.module

val dataModule = module {
    single<PopularMovieRepository> { PopularMovieRepositoryImpl(movieApi = get()) }
    single<GenreRepository> { GenreRepositoryImpl(searchAndGenreApi = get()) }
    single<SearchMovieRepository> { SearchMovieRepositoryImpl(searchAndGenreApi = get()) }
}