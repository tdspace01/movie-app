package com.example.movieapp.favourite.favouritescreen

import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.movieapp.ui.base.BaseViewModel
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.movie.GetFavouriteMoviesUseCase

class FavoriteViewModel(
    private val getFavoriteMoviesUseCase: GetFavouriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase
) : BaseViewModel<FavouriteState, FavoriteEvent, FavoriteSideEffect>(FavouriteState()) {

    init {
        onEvent(FavoriteEvent.ObserveFavorites)
    }

    override fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.ObserveFavorites -> observeFavorites()
            is FavoriteEvent.OnRemoveFavorite -> handleRemoveFavorite(event.movie)
            is FavoriteEvent.OnMovieClick -> handleMovieClick(event.movieId, event.category)
            is FavoriteEvent.OnHomeClick -> handleHomeClick()
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

    private fun handleMovieClick(movieId: Int, category: String) {
        emitSideEffect(FavoriteSideEffect.NavigateToDetail(movieId, category))
    }

    private fun handleHomeClick() {
        emitSideEffect(FavoriteSideEffect.NavigateToHome)
    }
}