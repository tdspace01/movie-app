package com.example.movieapp.data.di

import com.example.movieapp.domain.usecase.GenresUseCase
import com.example.movieapp.domain.usecase.MoviesByGenreUseCase
import com.example.movieapp.domain.usecase.PopularMoviesUseCase
import com.example.movieapp.domain.usecase.SearchMoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { PopularMoviesUseCase(repository = get()) }
    factory { GenresUseCase(repository = get()) }
    factory { MoviesByGenreUseCase(repository = get()) }
    factory { SearchMoviesUseCase(repository = get()) }
}