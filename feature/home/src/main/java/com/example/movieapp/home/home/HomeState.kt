package com.example.movieapp.home.home

import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.home.home.move_mode.MovieListMode

data class HomeState(
    val searchQuery: String = "",
    val activeSearchQuery: String = "",
    val selectedGenreId: Int? = null,
    val isGenresExpanded: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val refreshKey: Int = 0,
    val errorType: NetworkError? = null,
) {
    val listMode: MovieListMode?
        get() = when {
            errorType == NetworkError.NO_INTERNET || genres.isEmpty() -> null
            activeSearchQuery.isNotBlank() -> MovieListMode.Search(activeSearchQuery)
            selectedGenreId != null -> MovieListMode.ByGenre(selectedGenreId)
            else -> MovieListMode.Popular
        }
}