package com.example.movieapp.favourite.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.favourite.presentation.contract.FavouriteEvent

@Composable
internal fun FavouriteMoviesList(
    movies: List<PopularMovie>,
    onEvent: (FavouriteEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = MovieAppSizing.size20),
    ) {
        items(movies.chunked(2)) { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MovieAppSizing.size16,
                        vertical = MovieAppSizing.size8,
                    ),
                horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size16),
            ) {
                row.forEach { movie ->
                    Box(modifier = Modifier.weight(1f)) {
                        FavouriteMovieItem(
                            movie = movie,
                            onMovieClick = {
                                onEvent(
                                    FavouriteEvent.OnMovieClick(movie.id, movie.category)
                                )
                            },
                            onRemoveFavorite = {
                                onEvent(FavouriteEvent.OnRemoveFavorite(movie))
                            },
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