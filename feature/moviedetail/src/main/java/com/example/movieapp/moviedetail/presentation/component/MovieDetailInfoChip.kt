package com.example.movieapp.moviedetail.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppLineHeight
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme

@Composable
internal fun MovieDetailInfoChip(
    text: String?,
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int? = null,
) {
    if (text.isNullOrBlank()) return

    Box(
        modifier = modifier
            .clip(MovieAppShapes.corner50)
            .background(DarkColorScheme.darkestGrey)
            .padding(horizontal = MovieAppSpacing.spacing10, vertical = MovieAppSpacing.spacing04),
        contentAlignment = Alignment.Center,
    ) {
        if (iconRes != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size4),
            ) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(MovieAppSizing.size16),
                )
                MovieAppText(
                    text = text,
                    fontSize = MovieAppFontSize.font14,
                    fontWeight = FontWeight.Medium,
                    color = DarkColorScheme.lightGrey,
                    lineHeight = MovieAppLineHeight.line18,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            MovieAppText(
                text = text,
                fontSize = MovieAppFontSize.font14,
                fontWeight = FontWeight.Medium,
                color = DarkColorScheme.lightGrey,
                lineHeight = MovieAppLineHeight.line18,
                textAlign = TextAlign.Center,
            )
        }
    }
}