package com.example.movieapp.favourite.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.favourite.R

@Composable
internal fun FavouriteEmptyState(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(R.drawable.no_result_icon),
            contentDescription = null,
            modifier = Modifier.size(MovieAppSizing.size106),
        )
        Spacer(modifier = Modifier.height(MovieAppSpacing.spacing12))
        MovieAppText(
            text = stringResource(R.string.no_favorite),
            fontSize = MovieAppFontSize.font16,
            color = DarkColorScheme.lightGrey,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Preview
@Composable
private fun FavouriteEmptyStateP(){
    FavouriteEmptyState()
}