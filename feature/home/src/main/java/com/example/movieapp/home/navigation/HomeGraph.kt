package com.example.movieapp.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.activity.compose.BackHandler
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel
import com.example.movieapp.home.home.HomeScreen
import com.example.movieapp.home.home.HomeViewModel
import com.example.movieapp.navigation.home.HomeRoute

fun NavGraphBuilder.homeGraph(
    onNavigateToDetail: (Int, String) -> Unit,
    onNavigateToFavorite: () -> Unit
) {
    composable<HomeRoute.Home> {
        //BackHandler(enabled = true) {}
        val viewModel: HomeViewModel = koinViewModel()
        HomeScreen(
            viewModel = viewModel,
            onNavigateToDetail = onNavigateToDetail,
            onNavigateToFavorite = onNavigateToFavorite
        )
    }
}