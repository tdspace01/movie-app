package com.example.movieapp.moviedetail.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppLineHeight
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.moviedetail.R
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailEvent

@Composable
internal fun MovieDetailHeader(
    onEvent: (MovieDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = MovieAppSpacing.spacing16,
                vertical = MovieAppSpacing.spacing12,
            ),
    ) {
        Image(
            painter = painterResource(R.drawable.arrow_back),
            contentDescription = null,
            modifier = Modifier
                .width(MovieAppSizing.size10)
                .height(MovieAppSizing.size18)
                .align(Alignment.CenterStart)
                .clickable { onEvent(MovieDetailEvent.OnBackClick) },
        )
        MovieAppText(
            text = stringResource(R.string.details),
            fontSize = MovieAppFontSize.font16,
            fontWeight = FontWeight.SemiBold,
            color = DarkColorScheme.whisper,
            textAlign = TextAlign.Center,
            lineHeight = MovieAppLineHeight.line18,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview
@Composable
private fun MovieDetailHeaderP(){
    MovieDetailHeader(
        onEvent = {},
        modifier = Modifier
    )
}