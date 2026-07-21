package com.example.movieapp.data.di

import com.example.movieapp.data.remote.network.PopularMovieApi
import com.example.movieapp.data.remote.network.SearchAndGenreApi
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    single <PopularMovieApi>{ get<Retrofit>().create(PopularMovieApi::class.java) }
    single <SearchAndGenreApi>{ get<Retrofit>().create(SearchAndGenreApi::class.java) }
}