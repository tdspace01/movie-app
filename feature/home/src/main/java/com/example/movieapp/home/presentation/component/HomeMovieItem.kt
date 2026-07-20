package com.example.movieapp.home.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.movieapp.designsystem.components.MovieCard
import com.example.movieapp.domain.model.movie.PopularMovie

@Composable
internal fun HomeMovieItem(
    movie: PopularMovie,
    onMovieClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MovieCard(
        title = movie.title,
        imageUrl = movie.posterUrl,
        subtitle = movie.year,
        badgeText = movie.category.takeUnless { it.equals("n/a", ignoreCase = true) },
        favoriteIcon = painterResource(
            if (movie.isFavorite) {
                com.example.movieapp.designsystem.R.drawable.small_marked_heart
            } else {
                com.example.movieapp.designsystem.R.drawable.small_unmarked_heart
            }
        ),
        onCardClick = onMovieClick,
        onFavoriteClick = onFavoriteClick,
        modifier = modifier,
    )
}