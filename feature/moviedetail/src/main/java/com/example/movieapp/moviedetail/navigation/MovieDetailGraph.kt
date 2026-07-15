package com.example.movieapp.moviedetail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movieapp.moviedetail.moviedetailscreen.MovieDetailScreen
import com.example.movieapp.navigation.moviedetail.MovieDetailRoute

fun NavGraphBuilder.movieDetailGraph(
    onNavigateBack:() -> Unit
){
    composable<MovieDetailRoute.MovieDetail> {
        MovieDetailScreen(
            onNavigateBack = onNavigateBack
        )
    }
}