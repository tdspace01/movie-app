package com.example.movieapp.favourite.navigation

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movieapp.favourite.favourite_screen.FavouriteScreen
import com.example.movieapp.navigation.favourite.FavouriteRoute

fun NavGraphBuilder.favouriteGraph(
    onNavigateToDetails:(Int) -> Unit
){
    composable<FavouriteRoute.Favourite> {
        BackHandler(enabled = true) {}
        FavouriteScreen(
            onNavigateToDetails = onNavigateToDetails
        )
    }
}