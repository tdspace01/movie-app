package com.example.movieapp.home.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.designsystem.components.ChipItem
import com.example.movieapp.designsystem.components.MovieAppCategoryChip
import com.example.movieapp.designsystem.components.MovieAppLoader
import com.example.movieapp.designsystem.components.MovieAppNavigationButton
import com.example.movieapp.designsystem.components.MovieAppNetworkConnectionScreen
import com.example.movieapp.designsystem.components.MovieAppSearchBar
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.components.MovieCard
import com.example.movieapp.designsystem.components.MovieCardShimmer
import com.example.movieapp.designsystem.components.MovieTab
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.home.R
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (Int, String) -> Unit,
    onNavigateToFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var hasLoadedMoviesOnce by remember { mutableStateOf(false) }

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
        pagedMovies = viewModel.pagedMovies,
        hasLoadedMoviesOnce = hasLoadedMoviesOnce,
        onMoviesLoaded = { hasLoadedMoviesOnce = true },
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    pagedMovies: Flow<PagingData<PopularMovie>>,
    hasLoadedMoviesOnce: Boolean,
    onMoviesLoaded: () -> Unit,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val showFullError = !state.isRefreshing && (
            state.errorType != null ||
                    (state.requiresManualRefresh && !hasLoadedMoviesOnce)
            )
    val showOfflineBanner = state.isOffline && hasLoadedMoviesOnce && !state.isRefreshing
    var isSearchBarVisible by remember { mutableStateOf(true) }
    var isInitialLoading by remember { mutableStateOf(false) }
    var isGridAtTop by remember { mutableStateOf(true) }

    val isGenresExpanded = state.isGenresExpanded
    val dynamicTopPadding by animateDpAsState(
        targetValue = if (isGenresExpanded) MovieAppSizing.size100 else MovieAppSizing.size70,
        label = stringResource(R.string.list_padding_animation)
    )
    val gridBottomPadding by animateDpAsState(
        targetValue = if (showOfflineBanner) MovieAppSizing.size106 else MovieAppSizing.size80,
        label = stringResource(R.string.grid_bottom_padding)
    )

    LaunchedEffect(showFullError, state.isRefreshing, isInitialLoading, isGridAtTop) {
        isSearchBarVisible =
            showFullError || state.isRefreshing || isInitialLoading || isGridAtTop
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.ime)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isRefreshing -> {
                    MovieAppLoader(modifier = Modifier.fillMaxSize())
                }

                showFullError -> {
                    MovieAppNetworkConnectionScreen(
                        onRefresh = { onEvent(HomeEvent.OnRefresh) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    key(state.listMode, state.refreshKey) {
                        HomeMoviesGrid(
                            state = state,
                            pagedMovies = pagedMovies,
                            dynamicTopPadding = dynamicTopPadding,
                            bottomPadding = gridBottomPadding,
                            onMoviesLoaded = onMoviesLoaded,
                            onInitialLoadingChanged = { isInitialLoading = it },
                            onGridAtTopChanged = { isGridAtTop = it },
                            onEvent = onEvent,
                        )
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
                    isFilterActive = isGenresExpanded,
                    enabled = !showFullError && !state.isRefreshing &&
                            !isInitialLoading && !state.isOffline,
                    onQueryChanged = { onEvent(HomeEvent.OnSearchQueryChanged(it)) },
                    onFilterClick = {
                        if (!showFullError && !state.isRefreshing &&
                            !isInitialLoading && !state.isOffline
                        ) {
                            onEvent(HomeEvent.OnToggleGenresVisibility)
                        }
                    },
                    onClearClick = { onEvent(HomeEvent.OnClearSearch) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MovieAppSizing.size16,
                            vertical = MovieAppSizing.size15
                        )
                )

                AnimatedVisibility(
                    visible = isGenresExpanded && !showFullError &&
                            !state.isRefreshing && !state.isOffline,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    GenreRow(
                        genres = state.genres,
                        selectedGenreId = state.selectedGenreId,
                        onGenreSelected = { onEvent(HomeEvent.OnGenreSelected(it)) },
                        onGenreCleared = { onEvent(HomeEvent.OnGenreCleared) }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(1f)
                .fillMaxWidth()
        ) {
            AnimatedVisibility(
                visible = showOfflineBanner,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            ) {
                OfflineConnectionBanner()
            }

            MovieAppNavigationButton(
                currentTab = MovieTab.HOME,
                onTabSelected = { selectedTab ->
                    if (selectedTab == MovieTab.FAVORITES) onEvent(HomeEvent.OnFavoriteClick)
                },
            )
        }
    }
}

@Composable
private fun OfflineConnectionBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkColorScheme.black)
            .padding(
                horizontal = MovieAppSizing.size16,
                vertical = MovieAppSizing.size12,
            ),
        contentAlignment = Alignment.Center,
    ) {
        MovieAppText(
            text = stringResource(R.string.no_internet_connection),
            fontSize = MovieAppFontSize.font14,
            fontWeight = FontWeight.Medium,
            color = DarkColorScheme.primaryYellow,
            textAlign = TextAlign.Center,
        )
    }
}

@SuppressLint("FrequentlyChangingValue")
@Composable
private fun HomeMoviesGrid(
    state: HomeState,
    pagedMovies: Flow<PagingData<PopularMovie>>,
    dynamicTopPadding: Dp,
    bottomPadding: Dp,
    onMoviesLoaded: () -> Unit,
    onInitialLoadingChanged: (Boolean) -> Unit,
    onGridAtTopChanged: (Boolean) -> Unit,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyPagingItems = pagedMovies.collectAsLazyPagingItems()
    val lazyGridState = rememberLazyGridState()
    val isInitialLoading = lazyPagingItems.loadState.refresh is LoadState.Loading
            && lazyPagingItems.itemCount == 0

    LaunchedEffect(lazyPagingItems.itemCount) {
        if (lazyPagingItems.itemCount > 0) {
            onMoviesLoaded()
        }
    }

    LaunchedEffect(isInitialLoading) {
        onInitialLoadingChanged(isInitialLoading)
    }

    LaunchedEffect(
        lazyGridState.firstVisibleItemIndex,
        lazyGridState.firstVisibleItemScrollOffset,
    ) {
        onGridAtTopChanged(
            lazyGridState.firstVisibleItemIndex == 0
                    && lazyGridState.firstVisibleItemScrollOffset == 0
        )
    }

    if (isInitialLoading) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = MovieAppSizing.size16, end = MovieAppSizing.size16,
                top = dynamicTopPadding, bottom = bottomPadding
            ),
            horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size16),
            verticalArrangement = Arrangement.spacedBy(MovieAppSpacing.spacing12),
            userScrollEnabled = false
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                MoviesSectionTitle()
            }
            items(6) {
                MovieCardShimmer()
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = lazyGridState,
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = MovieAppSizing.size16, end = MovieAppSizing.size16,
                top = dynamicTopPadding, bottom = bottomPadding
            ),
            horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size16),
            verticalArrangement = Arrangement.spacedBy(MovieAppSpacing.spacing12)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                MoviesSectionTitle()
            }

            val itemCount = lazyPagingItems.itemCount
            if (state.activeSearchQuery.isNotBlank() && itemCount == 0) {
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
                            modifier = Modifier.fillMaxWidth(),
                            onMovieClick = {
                                onEvent(
                                    HomeEvent.OnMovieClick(
                                        movie.id,
                                        movie.category
                                    )
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

@Composable
private fun MoviesSectionTitle(modifier: Modifier = Modifier) {
    MovieAppText(
        text = stringResource(R.string.movies_home),
        fontSize = MovieAppFontSize.font18,
        color = DarkColorScheme.primaryYellow,
        modifier = modifier.padding(top = MovieAppSizing.size8),
    )
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
        enabled = enabled,
        showDeleteIcon = true,
        onDeleteLastCharacter = { onQueryChanged(query.dropLast(1)) },
    )
}

@Composable
private fun GenreRow(
    genres: List<Genre>,
    selectedGenreId: Int?,
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