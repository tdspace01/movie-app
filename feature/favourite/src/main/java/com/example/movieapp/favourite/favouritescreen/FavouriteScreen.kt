package com.example.movieapp.favourite.favouritescreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieapp.designsystem.components.MovieAppNavigationButton
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.components.MovieCard
import com.example.movieapp.designsystem.components.MovieTab
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.domain.model.movie.PopularMovie

@Composable
fun FavouriteScreen(
    viewModel: FavoriteViewModel,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Int, String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is FavoriteSideEffect.NavigateToDetail -> {
                    onNavigateToDetails(effect.movieId, effect.category)
                }
                is FavoriteSideEffect.NavigateToHome -> onNavigateToHome()
            }
        }
    }

    FavouriteScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@SuppressLint("FrequentlyChangingValue")
@Composable
private fun FavouriteScreenContent(
    state: FavouriteState,
    modifier: Modifier = Modifier,
    onEvent: (FavoriteEvent) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    var isHeaderVisible by remember { mutableStateOf(true) }

    val dynamicTopPadding by animateDpAsState(
        targetValue = if (isHeaderVisible) 50.dp else 0.dp,
        label = "ListPaddingAnimation"
    )

    LaunchedEffect(lazyListState.firstVisibleItemIndex,
        lazyListState.firstVisibleItemScrollOffset) {
        val isAtAbsoluteTop = lazyListState.firstVisibleItemIndex == 0
                && lazyListState.firstVisibleItemScrollOffset == 0
        isHeaderVisible = isAtAbsoluteTop
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
                state.favoriteMovies.isEmpty() -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Image(
                            painter = painterResource(
                                com.example.movieapp.designsystem.R.drawable.no_result_icon
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(106.dp)
                        )
                        Spacer(modifier = Modifier.height(MovieAppSpacing.spacing12))
                        MovieAppText(
                            text = "No favorites added yet",
                            fontSize = MovieAppFontSize.font16,
                            color = DarkColorScheme.lightGrey,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = dynamicTopPadding, bottom = 80.dp)
                    ) {
                        items(state.favoriteMovies.chunked(2)) { row ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                row.forEach { movie ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        FavoriteMovieItem(
                                            movie = movie,
                                            onMovieClick = {
                                                onEvent(
                                                    FavoriteEvent.OnMovieClick(
                                                        movie.id,
                                                        movie.category
                                                    )
                                                )
                                            },
                                            onRemoveFavorite = {
                                                onEvent(FavoriteEvent.OnRemoveFavorite(movie))
                                            }
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

        AnimatedVisibility(
            visible = isHeaderVisible && state.favoriteMovies.isNotEmpty(),
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            MovieAppText(
                text = "Favorite movies",
                fontSize = MovieAppFontSize.font16,
                color = DarkColorScheme.whisper,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

        MovieAppNavigationButton(
            currentTab = MovieTab.FAVORITES,
            onTabSelected = { selectedTab ->
                if (selectedTab == MovieTab.HOME) {
                    onEvent(FavoriteEvent.OnHomeClick)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun FavoriteMovieItem(
    movie: PopularMovie,
    onMovieClick: (Int) -> Unit,
    onRemoveFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    MovieCard(
        title = movie.title,
        imageUrl = movie.posterUrl,
        subtitle = movie.year,
        badgeText = movie.category.ifEmpty { null },
        favoriteIcon = painterResource(
            com.example.movieapp.designsystem.R.drawable.big_marked_heart
        ),
        onCardClick = { onMovieClick(movie.id) },
        onFavoriteClick = onRemoveFavorite,
        modifier = modifier
    )
}