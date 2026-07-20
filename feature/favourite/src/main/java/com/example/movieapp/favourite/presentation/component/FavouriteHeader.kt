package com.example.movieapp.favourite.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.favourite.R

@Composable
internal fun FavouriteHeader(modifier: Modifier = Modifier) {
    MovieAppText(
        text = stringResource(R.string.favorite_movie),
        fontSize = MovieAppFontSize.font16,
        color = DarkColorScheme.whisper,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MovieAppSizing.size16),
    )
}