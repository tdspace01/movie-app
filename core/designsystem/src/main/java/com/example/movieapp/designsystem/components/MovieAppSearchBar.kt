package com.example.movieapp.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.movieapp.designsystem.R
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.designsystem.theme.Montserrat

@Composable
fun MovieAppSearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    placeholder: String,
    isFilterActive: Boolean,
    onFilterClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showDeleteIcon: Boolean = false,
    onDeleteLastCharacter: () -> Unit = {},
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
                .padding(
                    horizontal = MovieAppSpacing.spacing24,
                    vertical = MovieAppSpacing.spacing09
                ),
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
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MovieAppSpacing.spacing08)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.search_loop),
                            contentDescription = null,
                            modifier = Modifier.size(MovieAppSizing.size18)
                        )

                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {

                            if (query.isEmpty()) {
                                MovieAppText(
                                    text = placeholder,
                                    fontSize = MovieAppFontSize.font14,
                                    color = DarkColorScheme.lightGrey
                                )
                            }
                            innerTextField()
                        }

                        if (showDeleteIcon && query.isNotEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.search_clear_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(MovieAppSizing.size10)
                                    .clickable(enabled = enabled) { onDeleteLastCharacter() }
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (query.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .size(MovieAppSizing.size36)
                    .clip(CircleShape)
                    .clickable(enabled = enabled) { onClearClick() },
                contentAlignment = Alignment.Center
            ) {
                MovieAppText(
                    text = stringResource(R.string.cancel),
                    fontSize = MovieAppFontSize.font10,
                    color = DarkColorScheme.whisper
                )
            }

        } else {
            Box(
                modifier = Modifier
                    .size(MovieAppSizing.size36)
                    .clip(CircleShape)
                    .clickable(enabled = enabled) { onFilterClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = if (isFilterActive)
                            R.drawable.selected_filter
                        else
                            R.drawable.unselected_filter
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(MovieAppSizing.size36)
                )
            }
        }
    }
}