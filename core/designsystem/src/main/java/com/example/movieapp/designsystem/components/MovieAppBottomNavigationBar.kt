package com.example.movieapp.designsystem.components

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import com.example.movieapp.designsystem.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.designsystem.design.MovieAppFontSize

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
            .height(62.dp)
            .background(DarkColorScheme.black)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavTab(
            label = "Home",
            selectedIconRes = R.drawable.home_marked_icon,
            unselectedIconRes = R.drawable.home_unmarked_icon,
            isSelected = currentTab == MovieTab.HOME,
            onClick = { onTabSelected(MovieTab.HOME) },
            modifier = Modifier.weight(1f)
        )

        NavTab(
            label = "Favorites",
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
            .height(38.dp)
            .clip(MovieAppShapes.corner8)
            .background(
                if (isSelected) DarkColorScheme.primaryYellow
                            else DarkColorScheme.darkestGrey
            )
            .clickable { onClick() }
            .padding(horizontal = 42.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = if (isSelected) selectedIconRes else unselectedIconRes),
            contentDescription = label,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        MovieAppText(
            text = label,
            fontSize = MovieAppFontSize.font14,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) DarkColorScheme.black else DarkColorScheme.lightGrey
        )
    }
}
