package com.example.movieapp.data.di

import com.example.movieapp.data.repository_implementation.MovieRepositoryImpl
import com.example.movieapp.domain.repository.MovieRepository
import org.koin.dsl.module

val dataModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            movieApi = get(),
            responseHandler = get()
        )
    }
}