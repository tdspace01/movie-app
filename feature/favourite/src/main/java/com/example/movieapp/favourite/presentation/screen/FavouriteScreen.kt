package com.example.movieapp.favourite.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieapp.designsystem.components.MovieAppNavigationButton
import com.example.movieapp.designsystem.components.MovieTab
import com.example.movieapp.favourite.presentation.component.FavouriteEmptyState
import com.example.movieapp.favourite.presentation.component.FavouriteHeader
import com.example.movieapp.favourite.presentation.component.FavouriteMoviesList
import com.example.movieapp.favourite.presentation.contract.FavouriteEvent
import com.example.movieapp.favourite.presentation.contract.FavouriteSideEffect
import com.example.movieapp.favourite.presentation.contract.FavouriteState
import com.example.movieapp.favourite.presentation.vm.FavouriteViewModel

@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToDetails: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is FavouriteSideEffect.NavigateToDetail ->
                    onNavigateToDetails(effect.movieId, effect.category)
                is FavouriteSideEffect.NavigateToHome ->
                    onNavigateToHome()
            }
        }
    }

    FavouriteScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
private fun FavouriteScreenContent(
    uiState: FavouriteState,
    onEvent: (FavouriteEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
    ) {
        FavouriteHeader()

        Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            if (uiState.isEmpty) {
                FavouriteEmptyState()
            } else {
                FavouriteMoviesList(
                    movies = uiState.favoriteMovies,
                    onEvent = onEvent,
                )
            }
        }

        MovieAppNavigationButton(
            currentTab = MovieTab.FAVORITES,
            onTabSelected = { selectedTab ->
                if (selectedTab == MovieTab.HOME) {
                    onEvent(FavouriteEvent.OnHomeClick)
                }
            },
        )
    }
}