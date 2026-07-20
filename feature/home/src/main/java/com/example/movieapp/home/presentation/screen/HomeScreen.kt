package com.example.movieapp.home.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.designsystem.components.MovieAppLoader
import com.example.movieapp.designsystem.components.MovieAppNavigationButton
import com.example.movieapp.designsystem.components.MovieAppNetworkConnectionScreen
import com.example.movieapp.designsystem.components.MovieAppOfflineBanner
import com.example.movieapp.designsystem.components.MovieTab
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.home.presentation.component.HomeMoviesList
import com.example.movieapp.home.presentation.component.HomeSearchHeader
import com.example.movieapp.home.presentation.contract.HomeEvent
import com.example.movieapp.home.presentation.contract.HomeSideEffect
import com.example.movieapp.home.presentation.contract.HomeState
import com.example.movieapp.home.presentation.vm.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (Int, String) -> Unit,
    onNavigateToFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.pagedMovies.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.NavigateToDetail ->
                    onNavigateToDetail(effect.movieId, effect.category)
                is HomeSideEffect.NavigateToFavorite ->
                    onNavigateToFavorite()
            }
        }
    }

    HomeScreenContent(
        uiState = uiState,
        lazyPagingItems = lazyPagingItems,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeState,
    lazyPagingItems: LazyPagingItems<PopularMovie>,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val bottomPadding = if (uiState.showOfflineBanner) {
        MovieAppSizing.size36
    } else {
        MovieAppSizing.size20
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.ime)
            .navigationBarsPadding()
            .statusBarsPadding(),
    ) {
        HomeSearchHeader(
            uiState = uiState,
            onEvent = onEvent,
        )

        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            when {
                uiState.isRefreshing -> {
                    MovieAppLoader(modifier = Modifier.fillMaxSize())
                }
                uiState.showFullError -> {
                    MovieAppNetworkConnectionScreen(
                        onRefresh = { onEvent(HomeEvent.OnRefresh) },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                else -> {
                    HomeMoviesList(
                        uiState = uiState,
                        lazyPagingItems = lazyPagingItems,
                        bottomPadding = bottomPadding,
                        onEvent = onEvent,
                    )
                }
            }
        }

        if (uiState.showOfflineBanner) {
            MovieAppOfflineBanner()
        }

        MovieAppNavigationButton(
            currentTab = MovieTab.HOME,
            onTabSelected = { selectedTab ->
                if (selectedTab == MovieTab.FAVORITES) {
                    onEvent(HomeEvent.OnFavoriteClick)
                }
            },
        )
    }
}