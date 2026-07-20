package com.example.movieapp.home.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.movieapp.designsystem.components.MovieCardShimmer
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.home.presentation.contract.HomeEvent
import com.example.movieapp.home.presentation.contract.HomeState

@Composable
internal fun HomeMoviesList(
    uiState: HomeState,
    lazyPagingItems: LazyPagingItems<PopularMovie>,
    bottomPadding: Dp,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isInitialLoading = lazyPagingItems.loadState.refresh is LoadState.Loading
            && lazyPagingItems.itemCount == 0

    LaunchedEffect(lazyPagingItems.itemCount) {
        if (lazyPagingItems.itemCount > 0) {
            onEvent(HomeEvent.OnMoviesLoaded)
        }
    }

    if (isInitialLoading) {
        HomeMoviesShimmerList(
            bottomPadding = bottomPadding,
            modifier = modifier,
        )
    } else {
        HomeMoviesContentList(
            uiState = uiState,
            lazyPagingItems = lazyPagingItems,
            bottomPadding = bottomPadding,
            onEvent = onEvent,
            modifier = modifier,
        )
    }
}

@Composable
private fun HomeMoviesShimmerList(
    bottomPadding: Dp,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = bottomPadding),
        userScrollEnabled = false,
    ) {
        item {
            HomeMoviesSectionTitle(
                modifier = Modifier.padding(horizontal = MovieAppSizing.size16),
            )
        }
        items(3) {
            HomeMovieShimmerRow()
        }
    }
}

@SuppressLint("FrequentlyChangingValue")
@Composable
private fun HomeMoviesContentList(
    uiState: HomeState,
    lazyPagingItems: LazyPagingItems<PopularMovie>,
    bottomPadding: Dp,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = bottomPadding),
    ) {
        item {
            HomeMoviesSectionTitle(
                modifier = Modifier.padding(horizontal = MovieAppSizing.size16),
            )
        }

        val itemCount = lazyPagingItems.itemCount
        if (uiState.activeSearchQuery.isNotBlank() && itemCount == 0) {
            item {
                HomeEmptySearchResult()
            }
        } else {
            val rowCount = (itemCount + 1) / 2
            items(count = rowCount) { rowIndex ->
                HomeMovieRow(
                    lazyPagingItems = lazyPagingItems,
                    leftIndex = rowIndex * 2,
                    onEvent = onEvent,
                )
            }
        }
    }
}

@Composable
private fun HomeMovieShimmerRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = MovieAppSizing.size16,
                vertical = MovieAppSizing.size8,
            ),
        horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size16),
    ) {
        MovieCardShimmer(modifier = Modifier.weight(1f))
        MovieCardShimmer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun HomeMovieRow(
    lazyPagingItems: LazyPagingItems<PopularMovie>,
    leftIndex: Int,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val rightIndex = leftIndex + 1
    val itemCount = lazyPagingItems.itemCount

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = MovieAppSizing.size16,
                vertical = MovieAppSizing.size8,
            ),
        horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size16),
    ) {
        HomeMovieListItem(
            lazyPagingItems = lazyPagingItems,
            index = leftIndex,
            onEvent = onEvent,
            modifier = Modifier.weight(1f),
        )
        if (rightIndex < itemCount) {
            HomeMovieListItem(
                lazyPagingItems = lazyPagingItems,
                index = rightIndex,
                onEvent = onEvent,
                modifier = Modifier.weight(1f),
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun HomeMovieListItem(
    lazyPagingItems: LazyPagingItems<PopularMovie>,
    index: Int,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val movie = lazyPagingItems[index]
    if (movie != null) {
        HomeMovieItem(
            movie = movie,
            onMovieClick = {
                onEvent(HomeEvent.OnMovieClick(movie.id, movie.category))
            },
            onFavoriteClick = { onEvent(HomeEvent.OnToggleFavorite(movie)) },
            modifier = modifier,
        )
    } else {
        MovieCardShimmer(modifier = modifier)
    }
}