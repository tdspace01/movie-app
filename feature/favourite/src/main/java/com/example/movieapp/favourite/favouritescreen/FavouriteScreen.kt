package com.example.movieapp.favourite.favouritescreen

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.statusBarsPadding
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.designsystem.components.MovieTab
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieapp.designsystem.components.MovieCard
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import com.example.movieapp.designsystem.theme.DarkColorScheme
import androidx.compose.foundation.layout.navigationBarsPadding
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.components.MovieAppNavigationButton

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

@Composable
private fun FavouriteScreenContent(
    state: FavouriteState,
    modifier: Modifier = Modifier,
    onEvent: (FavoriteEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        MovieAppText(
            text = "Favorite movies",
            fontSize = MovieAppFontSize.font16,
            color = DarkColorScheme.whisper,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 16.dp, bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (state.favoriteMovies.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(com.example.movieapp.designsystem.R.drawable.no_result_icon),
                        contentDescription = null,
                        modifier = modifier.size(106.dp)
                    )

                    Spacer(modifier = Modifier.height(MovieAppSpacing.spacing12))

                    MovieAppText(
                        text = "No favorites added yet",
                        fontSize = MovieAppFontSize.font16,
                        color = DarkColorScheme.lightGrey,
                        fontWeight = FontWeight.Normal
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.favoriteMovies, key = { it.id }) { movie ->
                        FavoriteMovieItem(
                            movie = movie,
                            onMovieClick = { onEvent(FavoriteEvent.OnMovieClick(movie.id, movie.category)) },
                            onRemoveFavorite = { onEvent(FavoriteEvent.OnRemoveFavorite(movie)) }
                        )
                    }
                }
            }
        }

        MovieAppNavigationButton(
            currentTab = MovieTab.FAVORITES,
            onTabSelected = { selectedTab ->
                if (selectedTab == MovieTab.HOME) {
                    onEvent(FavoriteEvent.OnHomeClick)
                }
            }
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
        favoriteIcon = painterResource(com.example.movieapp.designsystem.R.drawable.big_marked_heart),
        onCardClick = { onMovieClick(movie.id) },
        onFavoriteClick = onRemoveFavorite,
        modifier = modifier
    )
}