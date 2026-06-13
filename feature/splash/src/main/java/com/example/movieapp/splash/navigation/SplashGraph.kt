package com.example.movieapp.splash.navigation

import androidx.activity.compose.BackHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movieapp.navigation.splash.SplashRoute
import com.example.movieapp.splash.splash_screen.SplashScreen
import com.example.movieapp.splash.splash_screen.SplashViewModel

fun NavGraphBuilder.splashGraph(
    onNavigateToHome: () -> Unit
){
    composable<SplashRoute> {
        BackHandler(enabled = true) {}
        val vm: SplashViewModel = viewModel()
        SplashScreen(
            onNavigateToHome = onNavigateToHome,
            viewModel = vm
        )
    }
}