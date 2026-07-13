package com.example.movieapp.home.presentation

import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.home.presentation.movie_mode.MovieListMode

data class HomeState(
    val searchQuery: String = "",
    val activeSearchQuery: String = "",
    val selectedGenreId: Int? = null,
    val isGenresExpanded: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val refreshKey: Int = 0,
    val errorType: NetworkError? = null,
    val isOffline: Boolean = false,
    val isRefreshing: Boolean = false,
    val requiresManualRefresh: Boolean = false,
) {
    val listMode: MovieListMode?
        get() {
            if (genres.isEmpty()) return null
            return when {
                activeSearchQuery.isNotBlank() -> MovieListMode.Search(activeSearchQuery)
                selectedGenreId != null -> MovieListMode.ByGenre(selectedGenreId)
                else -> MovieListMode.Popular
            }
        }
}