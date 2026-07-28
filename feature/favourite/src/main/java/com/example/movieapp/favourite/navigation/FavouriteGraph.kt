package com.example.movieapp.favourite.navigation

import androidx.navigation.NavGraphBuilder
import androidx.activity.compose.BackHandler
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel
import com.example.movieapp.navigation.favourite.FavouriteRoute
import com.example.movieapp.favourite.favouritescreen.FavouriteScreen
import com.example.movieapp.favourite.favouritescreen.FavoriteViewModel

fun NavGraphBuilder.favouriteGraph(
    onNavigateToDetails: (Int, String) -> Unit,
    onNavigateToHome: () -> Unit
) {
    composable<FavouriteRoute.Favourite> {
        //BackHandler(enabled = true) {}

        val favoriteViewModel: FavoriteViewModel = koinViewModel()

        FavouriteScreen(
            viewModel = favoriteViewModel,
            onNavigateToDetails = onNavigateToDetails,
            onNavigateToHome = onNavigateToHome
        )
    }
}