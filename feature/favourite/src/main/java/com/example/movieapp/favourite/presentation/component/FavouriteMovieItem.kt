package com.example.movieapp.favourite.presentation.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.designsystem.components.MovieCard
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.favourite.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun FavouriteMovieItem(
    movie: PopularMovie,
    onMovieClick: () -> Unit,
    onRemoveFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isRemoving by remember(movie.id) { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isRemoving) 0f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = stringResource(R.string.smooth),
    )

    val scale by animateFloatAsState(
        targetValue = if (isRemoving) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = stringResource(R.string.smooth),
    )

    LaunchedEffect(isRemoving) {
        if (isRemoving) {
            delay(220.milliseconds)
            onRemoveFavorite()
        }
    }

    Box(
        modifier = modifier
            .scale(scale)
            .alpha(alpha),
    ) {
        MovieCard(
            title = movie.title,
            imageUrl = movie.posterUrl,
            subtitle = movie.year,
            badgeText = movie.category.ifEmpty { null },
            favoriteIcon = painterResource(
                com.example.movieapp.designsystem.R.drawable.small_marked_heart,
            ),
            onCardClick = { if (!isRemoving) onMovieClick() },
            onFavoriteClick = { isRemoving = true },
        )
    }
}

@Preview
@Composable
private fun FavouriteMovieItemPreview() {
    FavouriteMovieItem(
        movie = PopularMovie(
            id = 1,
            title = "Gela",
            posterUrl = "https://image.tmdb.org/t/p/w500/sample_poster.jpg",
            year = "2024",
            category = "Action",
            isFavorite = true,
        ),
        onMovieClick = {},
        onRemoveFavorite = {},
        modifier = Modifier,
    )
}