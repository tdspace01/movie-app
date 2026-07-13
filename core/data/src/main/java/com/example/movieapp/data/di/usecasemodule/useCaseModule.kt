package com.example.movieapp.data.di.usecasemodule

import com.example.movieapp.domain.usecase.movie.GetFavouriteMoviesUseCase
import com.example.movieapp.domain.usecase.movie.GetMovieDetailsUseCase
import com.example.movieapp.domain.usecase.movie.GetPopularMoviesPagedUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.network.ObserveNetworkStatusUseCase
import com.example.movieapp.domain.usecase.search.GetFavouriteIdsUseCase
import com.example.movieapp.domain.usecase.search.GetGenresUseCase
import com.example.movieapp.domain.usecase.search.GetMoviesByGenrePagedUseCase
import com.example.movieapp.domain.usecase.search.SearchMoviesPagedUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetGenresUseCase(repository = get()) }
    factory { GetFavouriteIdsUseCase(repository = get()) }
    factory { GetMovieDetailsUseCase(repository = get(), getFavouriteIdsUseCase = get()) }
    factory { ToggleFavouriteUseCase(repository = get()) }
    factory { GetFavouriteMoviesUseCase(repository = get()) }
    factory { ObserveNetworkStatusUseCase(networkObserver = get()) }
    factory { GetPopularMoviesPagedUseCase(repository = get()) }
    factory { SearchMoviesPagedUseCase(repository = get()) }
    factory { GetMoviesByGenrePagedUseCase(repository = get()) }
}