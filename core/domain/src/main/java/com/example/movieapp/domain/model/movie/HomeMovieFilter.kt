package com.example.movieapp.domain.model.movie

data class HomeMovieFilter(
    val activeSearchQuery: String = "",
    val selectedGenreId: Int? = null,
)

sealed interface MovieListSource {
    data object Popular : MovieListSource
    data class Search(val query: String) : MovieListSource
    data class ByGenre(val genreId: Int) : MovieListSource
}

fun HomeMovieFilter.resolve(): MovieListSource = when {
    activeSearchQuery.isNotBlank() -> MovieListSource.Search(activeSearchQuery)
    selectedGenreId != null -> MovieListSource.ByGenre(selectedGenreId)
    else -> MovieListSource.Popular
}