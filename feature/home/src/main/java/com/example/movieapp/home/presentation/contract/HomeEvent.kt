package com.example.movieapp.home.presentation.contract

import com.example.movieapp.domain.model.movie.PopularMovie

sealed interface HomeEvent {
    data object OnRefresh : HomeEvent
    data object OnGenreCleared : HomeEvent
    data object OnFavoriteClick : HomeEvent
    data object OnToggleGenresVisibility : HomeEvent
    data object OnMoviesLoaded : HomeEvent
    data class OnGenreSelected(val genreId: Int) : HomeEvent
    data class OnSearchQueryChanged(val query: String) : HomeEvent
    data class OnToggleFavorite(val movie: PopularMovie) : HomeEvent
    data class OnMovieClick(val movieId: Int, val category: String) : HomeEvent
}