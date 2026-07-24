package com.example.movieapp.home.presentation.contract

import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.ui.state.ErrorCapable
import com.example.movieapp.ui.state.OfflineCapable

data class HomeState(
    val searchQuery: String = "",
    val activeSearchQuery: String = "",
    val selectedGenreId: Int? = null,
    val isGenresExpanded: Boolean = false,
    val genres: List<Genre> = emptyList(),
    override val isOffline: Boolean = false,
    override val isRefreshing: Boolean = false,
    val hasLoadedContent: Boolean = false,
    override val showErrorScreen: Boolean = false,
) : OfflineCapable, ErrorCapable {
    val showOfflineBanner: Boolean
        get() = isOffline && hasLoadedContent && !isRefreshing

    val isSearchEnabled: Boolean
        get() = !showFullError && !isRefreshing && !isOffline

    val showGenreRow: Boolean
        get() = isGenresExpanded && isSearchEnabled
}