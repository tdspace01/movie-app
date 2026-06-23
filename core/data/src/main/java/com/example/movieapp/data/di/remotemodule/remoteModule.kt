package com.example.movieapp.data.di.remotemodule

import com.example.movieapp.data.remote.network.movie.MovieApi
import com.example.movieapp.data.remote.network.movie.MovieDetailApi
import com.example.movieapp.data.remote.network.search.SearchAndGenreApi
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    single <MovieApi>{ get<Retrofit>().create(MovieApi::class.java) }
    single <MovieDetailApi>{ get<Retrofit>().create(MovieDetailApi::class.java) }
    single <SearchAndGenreApi>{ get<Retrofit>().create(SearchAndGenreApi::class.java) }
}