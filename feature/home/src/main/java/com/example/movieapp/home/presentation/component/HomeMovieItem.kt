package com.example.movieapp.home.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.designsystem.R
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
                R.drawable.small_marked_heart
            } else {
                R.drawable.small_unmarked_heart
            }
        ),
        onCardClick = onMovieClick,
        onFavoriteClick = onFavoriteClick,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun HomeMovieItemP(){
    HomeMovieItem(
        movie = PopularMovie(
            id = 1,
            title = "Gela",
            posterUrl = "https://image.tmdb.org/t/p/w500/sample1.jpg",
            year = "2024",
            category = "Action",
            isFavorite = true,
        ),
        onMovieClick = {},
        onFavoriteClick = {},
        modifier = Modifier
    )
}