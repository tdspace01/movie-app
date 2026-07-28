package com.example.movieapp.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movieapp.designsystem.R
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppLineHeight
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme

data class ChipItem(
    val id: Int,
    val label: String
)

@Composable
fun MovieAppCategoryChip(
    items: List<ChipItem>,
    selectedId: Int?,
    isLoading: Boolean,
    onItemSelected: (Int) -> Unit,
    onClearSelected: () -> Unit,
    modifier: Modifier = Modifier,
    allLabel: String = stringResource(R.string.all)
) {
    if (isLoading) {
        MovieAppLoader()
        return
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = MovieAppSpacing.spacing16),
        horizontalArrangement = Arrangement.spacedBy(MovieAppSpacing.spacing08),
        modifier = modifier.fillMaxWidth().padding(bottom = MovieAppSpacing.spacing08)
    ) {
        item {
            CategoryChip(
                label = allLabel,
                selected = selectedId == null,
                onClick = onClearSelected
            )
        }

        items(items, key = { it.id }) { item ->
            CategoryChip(
                label = item.label,
                selected = selectedId == item.id,
                onClick = { onItemSelected(item.id) }
            )
        }
    }
}

@Composable
private fun CategoryChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(MovieAppShapes.corner22)
            .background(
                if (selected) DarkColorScheme.primaryYellow
                else DarkColorScheme.darkestGrey
            ).then(
                if (!selected) Modifier.border(
                    width = 0.5.dp,
                    color = DarkColorScheme.darkGrey,
                    shape = MovieAppShapes.corner22
                ) else Modifier
            )
            .clickable { onClick() }
            .padding(
                horizontal = MovieAppSpacing.spacing12,
                vertical = MovieAppSpacing.spacing04
            ),
        contentAlignment = Alignment.Center
    ) {
        MovieAppText(
            text = label,
            fontSize = MovieAppFontSize.font10,
            lineHeight = MovieAppLineHeight.line13,
            fontWeight = FontWeight.Medium,
            color = if (selected) DarkColorScheme.black else DarkColorScheme.lightGrey
        )
    }
}