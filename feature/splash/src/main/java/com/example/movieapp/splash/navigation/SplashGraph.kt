package com.example.movieapp.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movieapp.navigation.splash.SplashRoute
import com.example.movieapp.splash.presentation.SplashScreen
import com.example.movieapp.splash.presentation.SplashViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.splashGraph(
    onNavigateToHome: () -> Unit
) {
    composable<SplashRoute> {
        val viewModel: SplashViewModel = koinViewModel()
        SplashScreen(
            onNavigateToHome = onNavigateToHome,
            viewModel = viewModel
        )
    }
}