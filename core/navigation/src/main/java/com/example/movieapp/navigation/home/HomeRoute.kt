package com.example.movieapp.navigation.home

import kotlinx.serialization.Serializable

@Serializable
sealed class HomeRoute{
    @Serializable
    data object Home: HomeRoute()
}