package com.example.movieapp.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movieapp.home.home.HomeScreen
import com.example.movieapp.home.home.HomeViewModel
import com.example.movieapp.navigation.home.HomeRoute
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.homeGraph(
    onNavigateToDetail: (Int, String) -> Unit,
    onNavigateToFavorite: () -> Unit
) {
    composable<HomeRoute.Home> {
        val viewModel: HomeViewModel = koinViewModel()
        HomeScreen(
            viewModel = viewModel,
            onNavigateToDetail = onNavigateToDetail,
            onNavigateToFavorite = onNavigateToFavorite
        )
    }
}