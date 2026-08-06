package com.example.movieapp.moviedetail.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieapp.designsystem.components.MovieAppLoader
import com.example.movieapp.designsystem.components.MovieAppNetworkConnectionScreen
import com.example.movieapp.moviedetail.presentation.component.MovieDetailBody
import com.example.movieapp.moviedetail.presentation.component.MovieDetailHeader
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailEvent
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailSideEffect
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailState
import com.example.movieapp.moviedetail.presentation.vm.MovieDetailViewModel

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    category: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is MovieDetailSideEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    MovieDetailScreenContent(
        uiState = uiState,
        category = category,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
private fun MovieDetailScreenContent(
    uiState: MovieDetailState,
    category: String,
    onEvent: (MovieDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        MovieDetailHeader(onEvent = onEvent)

        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            when {
                uiState.isRefreshing || uiState.isLoading -> {
                    MovieAppLoader(modifier = Modifier.fillMaxSize())
                }
                uiState.showFullError -> {
                    MovieAppNetworkConnectionScreen(
                        onRefresh = { onEvent(MovieDetailEvent.OnRefresh) },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                uiState.showContent -> {
                    MovieDetailBody(
                        movie = uiState.movieDetail!!,
                        category = category,
                        onEvent = onEvent,
                    )
                }
            }
        }
    }
}