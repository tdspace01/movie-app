package com.example.movieapp.data.di.usecasemodule

import com.example.movieapp.domain.usecase.movie.GetFavouriteMoviesUseCase
import com.example.movieapp.domain.usecase.movie.GetMovieDetailsUseCase
import com.example.movieapp.domain.usecase.movie.GetPopularMoviesUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.search.GetGenresUseCase
import com.example.movieapp.domain.usecase.search.GetMoviesByGenreUseCase
import com.example.movieapp.domain.usecase.search.SearchMoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetPopularMoviesUseCase(repository = get(), favouriteMovieRepository = get()) }
    factory { GetGenresUseCase(repository = get()) }
    factory { GetMoviesByGenreUseCase(repository = get(), favouriteMovieRepository = get()) }
    factory { SearchMoviesUseCase(repository = get(), favouriteMovieRepository = get()) }
    factory { GetMovieDetailsUseCase(repository = get(),favouriteMovieRepository = get()) }
    factory { ToggleFavouriteUseCase(repository = get()) }
    factory { GetFavouriteMoviesUseCase(repository = get()) }
}