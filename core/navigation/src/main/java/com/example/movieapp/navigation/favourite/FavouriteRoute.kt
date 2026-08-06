package com.example.movieapp.navigation.favourite

import kotlinx.serialization.Serializable

@Serializable
sealed class FavouriteRoute{
    @Serializable
    data object Favourite: FavouriteRoute()
}