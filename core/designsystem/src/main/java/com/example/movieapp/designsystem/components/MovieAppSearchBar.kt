package com.example.movieapp.designsystem.components

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import com.example.movieapp.designsystem.R
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import com.example.movieapp.designsystem.theme.Montserrat
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.design.MovieAppFontSize

@Composable
fun MovieAppSearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    placeholder: String,
    isFilterActive: Boolean,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MovieAppSpacing.spacing08)
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .clip(MovieAppShapes.corner25)
                .background(DarkColorScheme.darkestGrey)
                .padding(horizontal = MovieAppSpacing.spacing24, vertical = MovieAppSpacing.spacing09),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = query,
                onValueChange = onQueryChanged,
                singleLine = true,
                enabled = enabled,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = DarkColorScheme.whisper,
                    fontSize = MovieAppFontSize.font14,
                    fontFamily = Montserrat
                ),
                cursorBrush = SolidColor(DarkColorScheme.whisper),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MovieAppSpacing.spacing08)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.search_loop),
                            contentDescription = null,
                            modifier = Modifier.size(MovieAppSizing.size18)
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (query.isEmpty()) {
                                if (query.isEmpty()) {
                                    MovieAppText(
                                        text = placeholder,
                                        fontSize = MovieAppFontSize.font14,
                                        color = DarkColorScheme.lightGrey
                                    )
                                }
                            }
                            innerTextField()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier.weight(0.12f)
                .aspectRatio(1f).clip(CircleShape)
                .background(
                    if (isFilterActive) DarkColorScheme.primaryYellow else DarkColorScheme.darkestGrey
                )
                .clickable(enabled = enabled) { onFilterClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.unselected_filter),
                contentDescription = "Filter",
                modifier = Modifier.size(MovieAppSizing.size18)
            )
        }
    }
}