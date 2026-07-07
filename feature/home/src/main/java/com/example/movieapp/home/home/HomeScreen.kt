package com.example.movieapp.home.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.designsystem.components.ChipItem
import com.example.movieapp.designsystem.components.MovieAppCategoryChip
import com.example.movieapp.designsystem.components.MovieAppNavigationButton
import com.example.movieapp.designsystem.components.MovieAppNetworkConnectionScreen
import com.example.movieapp.designsystem.components.MovieAppSearchBar
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.components.MovieCard
import com.example.movieapp.designsystem.components.MovieCardShimmer
import com.example.movieapp.designsystem.components.MovieTab
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.home.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (Int, String) -> Unit,
    onNavigateToFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
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
        state = state,
        lazyPagingItems = lazyPagingItems,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@SuppressLint("FrequentlyChangingValue")
@Composable
private fun HomeScreenContent(
    state: HomeState,
    lazyPagingItems: LazyPagingItems<PopularMovie>,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyGridState = rememberLazyGridState()
    val isError = state.errorType != null
    val isLoading = lazyPagingItems.loadState.refresh is LoadState.Loading

    var isSearchBarVisible by remember { mutableStateOf(true) }

    val isGenreVisible = state.isGenresVisible
    val dynamicTopPadding by animateDpAsState(
        targetValue = if (isGenreVisible) 100.dp else 70.dp,
        label = "ListPaddingAnimation"
    )

    LaunchedEffect(
        lazyGridState.firstVisibleItemIndex,
        lazyGridState.firstVisibleItemScrollOffset,
        isError,
        isLoading
    ) {
        isSearchBarVisible = if (isError || isLoading) {
            true
        } else {
            lazyGridState.firstVisibleItemIndex == 0
                    && lazyGridState.firstVisibleItemScrollOffset == 0
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isError -> {
                    MovieAppNetworkConnectionScreen(
                        onRefresh = { onEvent(HomeEvent.OnRefresh) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                isLoading -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = MovieAppSizing.size16, end = MovieAppSizing.size16,
                            top = dynamicTopPadding, bottom = MovieAppSizing.size80
                        ),
                        horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size16),
                        verticalArrangement = Arrangement.spacedBy(MovieAppSizing.size16),
                        userScrollEnabled = false
                    ) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            MovieAppText(
                                text = stringResource(R.string.movies_home),
                                fontSize = MovieAppFontSize.font18,
                                color = DarkColorScheme.primaryYellow,
                                modifier = Modifier.padding(vertical = MovieAppSizing.size8)
                            )
                        }
                        items(6) {
                            MovieCardShimmer()
                        }
                    }
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = lazyGridState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = MovieAppSizing.size16, end = MovieAppSizing.size16,
                            top = dynamicTopPadding, bottom = MovieAppSizing.size80
                        ),
                        horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size16),
                        verticalArrangement = Arrangement.spacedBy(MovieAppSizing.size16)
                    ) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            MovieAppText(
                                text = stringResource(R.string.movies_home),
                                fontSize = MovieAppFontSize.font18,
                                color = DarkColorScheme.primaryYellow,
                                modifier = Modifier.padding(vertical = MovieAppSizing.size8)
                            )
                        }

                        val itemCount = lazyPagingItems.itemCount
                        if (state.searchQuery.isNotBlank() && itemCount == 0) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = MovieAppSizing.size100),
                                    contentAlignment = Alignment.Center
                                ) {
                                    MovieAppText(
                                        text = stringResource(R.string.no_result),
                                        fontSize = MovieAppFontSize.font16,
                                        color = DarkColorScheme.lightGrey,
                                        modifier = Modifier.padding(
                                            horizontal = MovieAppSizing.size24
                                        )
                                    )
                                }
                            }
                        } else {
                            items(count = itemCount) { index ->
                                val movie = lazyPagingItems[index]
                                if (movie != null) {
                                    MovieItem(
                                        popularMovie = movie,
                                        onMovieClick = {
                                            onEvent(HomeEvent.OnMovieClick(
                                                movie.id,
                                                movie.category)
                                            )
                                        },
                                        onFavoriteClick = {
                                            onEvent(HomeEvent.OnToggleFavorite(movie))
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isSearchBarVisible,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                SearchBar(
                    query = state.searchQuery,
                    isFilterActive = state.isGenresVisible,
                    enabled = !isError,
                    onQueryChanged = { onEvent(HomeEvent.OnSearchQueryChanged(it)) },
                    onFilterClick = { if (!isError) onEvent(HomeEvent.OnToggleGenresVisibility) },
                    onClearClick = { onEvent(HomeEvent.OnClearSearch) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MovieAppSizing.size16,
                            vertical = MovieAppSizing.size15
                        )
                )

                AnimatedVisibility(
                    visible = isGenreVisible && !isError,
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
            }
        }

        MovieAppNavigationButton(
            currentTab = MovieTab.HOME,
            onTabSelected = { selectedTab ->
                if (selectedTab == MovieTab.FAVORITES) onEvent(HomeEvent.OnFavoriteClick)
            },
            modifier = Modifier.align(Alignment.BottomCenter)
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
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    MovieAppSearchBar(
        query = query,
        onQueryChanged = onQueryChanged,
        placeholder = stringResource(R.string.search),
        isFilterActive = isFilterActive,
        onFilterClick = onFilterClick,
        onClearClick = onClearClick,
        modifier = modifier,
        enabled = enabled
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
                com.example.movieapp.designsystem.R.drawable.small_marked_heart
            else
                com.example.movieapp.designsystem.R.drawable.small_unmarked_heart
        ),
        onCardClick = { onMovieClick(popularMovie.id) },
        onFavoriteClick = onFavoriteClick,
        modifier = modifier
    )
}