package com.example.movieapp.navigation.moviedetail

import kotlinx.serialization.Serializable

@Serializable
sealed class MovieDetailRoute{
    @Serializable
    data class MovieDetail(val movieId:Int): MovieDetailRoute()
}