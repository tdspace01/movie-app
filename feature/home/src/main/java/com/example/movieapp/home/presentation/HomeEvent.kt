package com.example.movieapp.home.presentation

import com.example.movieapp.domain.model.movie.PopularMovie

sealed interface HomeEvent {
    object OnRefresh : HomeEvent
    object OnClearSearch : HomeEvent
    object OnGenreCleared : HomeEvent
    object OnFavoriteClick : HomeEvent
    object OnToggleGenresVisibility : HomeEvent
    data class OnGenreSelected(val genreId: Int) : HomeEvent
    data class OnSearchQueryChanged(val query: String) : HomeEvent
    data class OnToggleFavorite(val movie: PopularMovie) : HomeEvent
    data class OnMovieClick(val movieId: Int, val category: String) : HomeEvent
}