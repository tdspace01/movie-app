package com.example.movieapp.home.navigation

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movieapp.home.home_screen.HomeScreen
import com.example.movieapp.navigation.home.HomeRoute

fun NavGraphBuilder.homeGraph(
    onNavigateToDetail:(Int) -> Unit
){
    composable<HomeRoute.Home> {
        BackHandler(enabled = true) {}
        HomeScreen(
            onNavigateToDetail = onNavigateToDetail
        )
    }

}