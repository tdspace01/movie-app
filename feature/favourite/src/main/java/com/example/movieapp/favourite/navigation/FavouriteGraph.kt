package com.example.movieapp.favourite.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movieapp.favourite.presentation.FavouriteViewModel
import com.example.movieapp.favourite.presentation.FavouriteScreen
import com.example.movieapp.navigation.favourite.FavouriteRoute
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.favouriteGraph(
    onNavigateToDetails: (Int, String) -> Unit,
    onNavigateToHome: () -> Unit
) {
    composable<FavouriteRoute.Favourite> {
        val favoriteViewModel: FavouriteViewModel = koinViewModel()

        FavouriteScreen(
            viewModel = favoriteViewModel,
            onNavigateToDetails = onNavigateToDetails,
            onNavigateToHome = onNavigateToHome
        )
    }
}