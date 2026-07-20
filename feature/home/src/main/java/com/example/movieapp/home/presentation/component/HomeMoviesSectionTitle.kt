package com.example.movieapp.home.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.home.R

@Composable
internal fun HomeMoviesSectionTitle(modifier: Modifier = Modifier) {
    MovieAppText(
        text = stringResource(R.string.movies_home),
        fontSize = MovieAppFontSize.font18,
        color = DarkColorScheme.primaryYellow,
        modifier = modifier.padding(top = MovieAppSizing.size8),
    )
}

@Composable
internal fun HomeEmptySearchResult(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = MovieAppSizing.size100),
        contentAlignment = Alignment.Center,
    ) {
        MovieAppText(
            text = stringResource(R.string.no_result),
            fontSize = MovieAppFontSize.font16,
            color = DarkColorScheme.lightGrey,
            modifier = Modifier.padding(horizontal = MovieAppSizing.size24),
        )
    }
}