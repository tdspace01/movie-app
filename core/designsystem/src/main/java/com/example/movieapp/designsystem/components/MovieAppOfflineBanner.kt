package com.example.movieapp.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.designsystem.R
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.theme.DarkColorScheme

@Composable
fun MovieAppOfflineBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkColorScheme.black)
            .padding(
                horizontal = MovieAppSizing.size16,
                vertical = MovieAppSizing.size12,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Image(
                painter = painterResource(id = R.drawable.alert_icon),
                contentDescription = null,
                modifier = Modifier.size(MovieAppSizing.size16),
            )

            Box(modifier = Modifier.width(MovieAppSizing.size8))

            MovieAppText(
                text = stringResource(R.string.no_internet_connection),
                fontSize = MovieAppFontSize.font14,
                fontWeight = FontWeight.Medium,
                color = DarkColorScheme.lightGrey,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Preview
@Composable
private fun MovieAppOfflineBannerP(){
    MovieAppOfflineBanner()
}