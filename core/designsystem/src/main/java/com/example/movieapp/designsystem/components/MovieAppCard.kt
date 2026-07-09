package com.example.movieapp.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppLineHeight
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme

@Composable
fun MovieCard(
    title: String,
    imageUrl: String?,
    subtitle: String,
    favoriteIcon: Painter,
    onCardClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    badgeText: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(163.5f / 226f)
        ) {
            MovieAppAsyncImage(
                imageUrl = imageUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxSize()
            )

            if (!badgeText.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = MovieAppSpacing.spacing10, end = MovieAppSpacing.spacing08)
                        .clip(MovieAppShapes.corner50)
                        .background(DarkColorScheme.primaryYellow)
                        .padding(
                            horizontal = MovieAppSpacing.spacing12,
                            vertical = MovieAppSpacing.spacing04
                        )
                ) {
                    MovieAppText(
                        text = badgeText,
                        fontSize = MovieAppFontSize.font12,
                        fontWeight = FontWeight.Medium,
                        color = DarkColorScheme.black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MovieAppSpacing.spacing04)
                .height(MovieAppSizing.size42)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MovieAppSizing.size18)
            ) {
                MovieAppText(
                    text = title,
                    fontSize = MovieAppFontSize.font14,
                    lineHeight = MovieAppLineHeight.line18,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = favoriteIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(MovieAppSpacing.spacing02)
                        .size(MovieAppSizing.size18)
                        .clickable { onFavoriteClick() }
                )
            }

            MovieAppText(
                text = subtitle,
                fontSize = MovieAppFontSize.font12,
                lineHeight = MovieAppLineHeight.line16,
                fontWeight = FontWeight.Normal,
                color = DarkColorScheme.lightGrey,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MovieAppSpacing.spacing04)
                    .height(MovieAppSizing.size16)
            )
        }
    }
}