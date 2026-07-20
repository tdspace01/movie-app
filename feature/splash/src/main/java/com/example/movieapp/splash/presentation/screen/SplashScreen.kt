package com.example.movieapp.splash.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.splash.R
import com.example.movieapp.splash.presentation.contract.SplashSideEffect
import com.example.movieapp.splash.presentation.vm.SplashViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                is SplashSideEffect.NavigateToHome -> onNavigateToHome()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.splash_logo),
            contentDescription = null,
            modifier = Modifier
                .width(MovieAppSizing.size80)
                .height(MovieAppSizing.size40)
        )
    }
}