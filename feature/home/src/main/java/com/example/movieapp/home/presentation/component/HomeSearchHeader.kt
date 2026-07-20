package com.example.movieapp.home.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movieapp.designsystem.components.ChipItem
import com.example.movieapp.designsystem.components.MovieAppCategoryChip
import com.example.movieapp.designsystem.components.MovieAppSearchBar
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.home.R
import com.example.movieapp.home.presentation.contract.HomeEvent
import com.example.movieapp.home.presentation.contract.HomeState

@Composable
internal fun HomeSearchHeader(
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        MovieAppSearchBar(
            query = uiState.searchQuery,
            onQueryChanged = { onEvent(HomeEvent.OnSearchQueryChanged(it)) },
            placeholder = stringResource(R.string.search),
            isFilterActive = uiState.isGenresExpanded,
            onFilterClick = {
                if (uiState.isSearchEnabled) {
                    onEvent(HomeEvent.OnToggleGenresVisibility)
                }
            },
            onClearClick = { onEvent(HomeEvent.OnSearchQueryChanged("")) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MovieAppSizing.size16,
                    vertical = MovieAppSizing.size15,
                ),
            enabled = uiState.isSearchEnabled,
            showDeleteIcon = true,
            onDeleteLastCharacter = {
                onEvent(
                    HomeEvent.OnSearchQueryChanged(uiState.searchQuery.dropLast(1))
                )
            },
        )

        if (uiState.showGenreRow) {
            MovieAppCategoryChip(
                items = uiState.genres.map { ChipItem(id = it.id, label = it.name) },
                selectedId = uiState.selectedGenreId,
                onItemSelected = { onEvent(HomeEvent.OnGenreSelected(it)) },
                onClearSelected = { onEvent(HomeEvent.OnGenreCleared) },
            )
        }
    }
}