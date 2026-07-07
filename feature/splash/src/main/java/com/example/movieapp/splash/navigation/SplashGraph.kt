package com.example.movieapp.splash.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movieapp.navigation.splash.SplashRoute
import com.example.movieapp.splash.splashscreen.SplashScreen
import com.example.movieapp.splash.splashscreen.SplashViewModel

fun NavGraphBuilder.splashGraph(
    onNavigateToHome: () -> Unit
){
    composable<SplashRoute> {
        val viewModel: SplashViewModel = viewModel()
        SplashScreen(
            onNavigateToHome = onNavigateToHome,
            viewModel = viewModel
        )
    }
}