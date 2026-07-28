package com.example.movieapp.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.movieapp.designsystem.R
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppLineHeight
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme

@Composable
fun MovieAppNetworkConnectionScreen(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkColorScheme.black)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = MovieAppSpacing.spacing70),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.alert_icon),
            contentDescription = null,
            modifier = Modifier.size(MovieAppSizing.size53)
        )

        Spacer(Modifier.height(MovieAppSpacing.spacing16))

        MovieAppText(
            text = stringResource(R.string.can_not_be_loaded),
            fontSize = MovieAppFontSize.font18,
            lineHeight = MovieAppLineHeight.line18,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(MovieAppSpacing.spacing08))

        MovieAppText(
            text = stringResource(R.string.internet_connection_error),
            fontSize = MovieAppFontSize.font16,
            fontWeight = FontWeight.Medium,
            color = DarkColorScheme.lightGrey,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )

        Spacer(Modifier.height(MovieAppSpacing.spacing70))

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .clip(MovieAppShapes.corner16)
                .background(DarkColorScheme.primaryYellow)
                .clickable { onRefresh() }
                .padding(
                    horizontal = MovieAppSpacing.spacing30,
                    vertical = MovieAppSpacing.spacing14
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MovieAppSpacing.spacing10)
        ) {
            MovieAppText(
                text = stringResource(R.string.refresh),
                fontSize = MovieAppFontSize.font16,
                fontWeight = FontWeight.Medium,
                color = DarkColorScheme.black
            )
            Image(
                painter = painterResource(R.drawable.refresh_icon),
                contentDescription = null,
                modifier = Modifier.size(MovieAppSizing.size16)
            )
        }
    }
}