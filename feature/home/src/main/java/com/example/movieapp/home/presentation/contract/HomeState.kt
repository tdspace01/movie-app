package com.example.movieapp.home.presentation.contract

import com.example.movieapp.domain.model.search.Genre

data class HomeState(
    val searchQuery: String = "",
    val activeSearchQuery: String = "",
    val selectedGenreId: Int? = null,
    val isGenresExpanded: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val isOffline: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasLoadedContent: Boolean = false,
    val showErrorScreen: Boolean = false,
) {
    val showFullError: Boolean
        get() = showErrorScreen && !isRefreshing

    val showOfflineBanner: Boolean
        get() = isOffline && hasLoadedContent && !isRefreshing

    val isSearchEnabled: Boolean
        get() = !showFullError && !isRefreshing && !isOffline

    val showGenreRow: Boolean
        get() = isGenresExpanded && isSearchEnabled
}