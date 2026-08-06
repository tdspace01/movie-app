package com.example.movieapp.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.designsystem.R
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.theme.DarkColorScheme

enum class MovieTab { HOME, FAVORITES }

@Composable
fun MovieAppNavigationButton(
    currentTab: MovieTab,
    onTabSelected: (MovieTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(MovieAppSizing.size62)
            .background(DarkColorScheme.black)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { /* click doesn't go through */ }
            .padding(horizontal = MovieAppSizing.size16, vertical = MovieAppSizing.size12),
        horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavTab(
            label = stringResource(R.string.home),
            selectedIconRes = R.drawable.home_marked_icon,
            unselectedIconRes = R.drawable.home_unmarked_icon,
            isSelected = currentTab == MovieTab.HOME,
            onClick = { onTabSelected(MovieTab.HOME) },
            modifier = Modifier.weight(1f)
        )

        NavTab(
            label = stringResource(R.string.favourite),
            selectedIconRes = R.drawable.favourite_marked_heart_icon,
            unselectedIconRes = R.drawable.favourite_unmarked_heart_icon,
            isSelected = currentTab == MovieTab.FAVORITES,
            onClick = { onTabSelected(MovieTab.FAVORITES) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun NavTab(
    label: String,
    selectedIconRes: Int,
    unselectedIconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(MovieAppSizing.size38)
            .clip(MovieAppShapes.corner8)
            .background(
                if (isSelected) DarkColorScheme.primaryYellow
                else DarkColorScheme.darkestGrey
            )
            .clickable { onClick() }
            .padding(horizontal = MovieAppSizing.size42, vertical = MovieAppSizing.size10),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = if (isSelected) selectedIconRes else unselectedIconRes),
            contentDescription = label,
            modifier = Modifier.size(MovieAppSizing.size18)
        )

        Spacer(modifier = Modifier.width(MovieAppSizing.size10))

        MovieAppText(
            text = label,
            fontSize = MovieAppFontSize.font14,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) DarkColorScheme.black else DarkColorScheme.lightGrey
        )
    }
}

@Preview
@Composable
private fun MovieAppNavigationButtonHomeSelectedP() {
    MovieAppNavigationButton(
        currentTab = MovieTab.HOME,
        onTabSelected = {},
    )
}

@Preview
@Composable
private fun MovieAppNavigationButtonFavoritesSelectedP() {
    MovieAppNavigationButton(
        currentTab = MovieTab.FAVORITES,
        onTabSelected = {},
    )
}