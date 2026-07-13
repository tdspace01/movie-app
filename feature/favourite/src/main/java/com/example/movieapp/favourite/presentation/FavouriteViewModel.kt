package com.example.movieapp.favourite.presentation

import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.movieapp.ui.base.BaseViewModel
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.movie.GetFavouriteMoviesUseCase

class FavouriteViewModel(
    private val getFavoriteMoviesUseCase: GetFavouriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase
) : BaseViewModel<FavouriteState, FavouriteEvent, FavouriteSideEffect>(FavouriteState()) {

    init {
        onEvent(FavouriteEvent.ObserveFavorites)
    }

    override fun onEvent(event: FavouriteEvent) {
        when (event) {
            is FavouriteEvent.ObserveFavorites -> observeFavorites()
            is FavouriteEvent.OnRemoveFavorite -> handleRemoveFavorite(event.movie)
            is FavouriteEvent.OnMovieClick -> {
                emitSideEffect(FavouriteSideEffect.NavigateToDetail(event.movieId, event.category))
            }

            is FavouriteEvent.OnHomeClick -> emitSideEffect(FavouriteSideEffect.NavigateToHome)

        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect { moviesList ->
                updateState { copy(favoriteMovies = moviesList) }
            }
        }
    }

    private fun handleRemoveFavorite(movie: PopularMovie) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
        }
    }
}