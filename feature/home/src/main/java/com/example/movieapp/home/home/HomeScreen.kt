package com.example.movieapp.home.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieapp.designsystem.components.ChipItem
import com.example.movieapp.designsystem.components.MovieAppCategoryChip
import com.example.movieapp.designsystem.components.MovieAppLoader
import com.example.movieapp.designsystem.components.MovieAppNavigationButton
import com.example.movieapp.designsystem.components.MovieAppNetworkConnectionScreen
import com.example.movieapp.designsystem.components.MovieAppSearchBar
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.components.MovieCard
import com.example.movieapp.designsystem.components.MovieTab
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.model.search.Genre

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (Int, String) -> Unit,
    onNavigateToFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.NavigateToDetail -> {
                    onNavigateToDetail(effect.movieId, effect.category)
                }
                is HomeSideEffect.NavigateToFavorite -> onNavigateToFavorite()
            }
        }
    }

    HomeScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    MovieAppLoader()
                }

                state.errorType != null -> {
                    MovieAppNetworkConnectionScreen(
                        onRefresh = { onEvent(HomeEvent.OnRefresh) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        item {
                            Column {
                                SearchBar(
                                    query = state.searchQuery,
                                    isFilterActive = state.isGenresVisible,
                                    onQueryChanged = { onEvent(HomeEvent.OnSearchQueryChanged(it)) },
                                    onFilterClick = { onEvent(HomeEvent.OnToggleGenresVisibility) },
                                    onClearClick = { onEvent(HomeEvent.OnClearSearch) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 15.dp)
                                )

                                AnimatedVisibility(
                                    visible = state.isGenresVisible,
                                    enter = expandVertically() + fadeIn(),
                                    exit = shrinkVertically() + fadeOut()
                                ) {
                                    GenreRow(
                                        genres = state.genres,
                                        selectedGenreId = state.selectedGenreId,
                                        isLoading = state.isGenresLoading,
                                        onGenreSelected = { onEvent(HomeEvent.OnGenreSelected(it)) },
                                        onGenreCleared = { onEvent(HomeEvent.OnGenreCleared) }
                                    )
                                }

                                MovieAppText(
                                    text = "Movies",
                                    fontSize = MovieAppFontSize.font18,
                                    color = DarkColorScheme.primaryYellow,
                                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp)
                                )
                            }
                        }

                        if (state.searchQuery.isNotBlank() && state.popularMovies.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillParentMaxHeight(0.7f)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    MovieAppText(
                                        text = "No results found for",
                                        fontSize = MovieAppFontSize.font16,
                                        color = DarkColorScheme.lightGrey,
                                        modifier = Modifier.padding(horizontal = 24.dp)
                                    )
                                }
                            }
                        } else {
                            items(state.popularMovies.chunked(2)) { row ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    row.forEach { movie ->
                                        Box(modifier = Modifier.weight(1f)) {
                                            MovieItem(
                                                popularMovie = movie,
                                                onMovieClick = { onEvent(HomeEvent.OnMovieClick(movieId = movie.id, category = movie.category)) },
                                                onFavoriteClick = { onEvent(HomeEvent.OnToggleFavorite(movie)) }
                                            )
                                        }
                                    }

                                    if (row.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        MovieAppNavigationButton(
            currentTab = MovieTab.HOME,
            onTabSelected = { selectedTab ->
                if (selectedTab == MovieTab.FAVORITES) {
                    onEvent(HomeEvent.OnFavoriteClick)
                }
            }
        )
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    isFilterActive: Boolean,
    onFilterClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MovieAppSearchBar(
        query = query,
        onQueryChanged = onQueryChanged,
        placeholder = "Search",
        isFilterActive = isFilterActive,
        onFilterClick = onFilterClick,
        onClearClick = onClearClick,
        modifier = modifier,
    )
}

@Composable
private fun GenreRow(
    genres: List<Genre>,
    selectedGenreId: Int?,
    isLoading: Boolean,
    onGenreSelected: (Int) -> Unit,
    onGenreCleared: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chipItems = remember(genres) {
        genres.map { ChipItem(id = it.id, label = it.name) }
    }

    MovieAppCategoryChip(
        items = chipItems,
        selectedId = selectedGenreId,
        isLoading = isLoading,
        onItemSelected = onGenreSelected,
        onClearSelected = onGenreCleared,
        modifier = modifier
    )
}

@Composable
private fun MovieItem(
    popularMovie: PopularMovie,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MovieCard(
        title = popularMovie.title,
        imageUrl = popularMovie.posterUrl,
        subtitle = popularMovie.year,
        badgeText = popularMovie.category,
        favoriteIcon = painterResource(
            if (popularMovie.isFavorite)
                com.example.movieapp.designsystem.R.drawable.big_marked_heart
            else
                com.example.movieapp.designsystem.R.drawable.big_unmarked_heart
        ),
        onCardClick = { onMovieClick(popularMovie.id) },
        onFavoriteClick = onFavoriteClick,
        modifier = modifier
    )
}