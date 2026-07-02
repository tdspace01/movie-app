package com.example.movieapp.moviedetail.navigation

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.movieapp.moviedetail.moviedetailscreen.MovieDetailScreen
import com.example.movieapp.moviedetail.moviedetailscreen.MovieDetailViewModel
import com.example.movieapp.navigation.moviedetail.MovieDetailRoute
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.movieDetailGraph(
    onNavigateBack: () -> Unit
) {
    composable<MovieDetailRoute.MovieDetail> { backStackEntry ->
        BackHandler(enabled = true) {}
        val detailRoute = backStackEntry.toRoute<MovieDetailRoute.MovieDetail>()
        val viewModel: MovieDetailViewModel = koinViewModel()
        MovieDetailScreen(
            viewModel = viewModel,
            onNavigateBack = onNavigateBack,
            category = detailRoute.category
        )
    }
}