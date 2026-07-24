package com.example.movieapp.favourite.presentation.vm

import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.usecase.movie.GetFavouriteMoviesUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.favourite.presentation.contract.FavouriteEvent
import com.example.movieapp.favourite.presentation.contract.FavouriteSideEffect
import com.example.movieapp.favourite.presentation.contract.FavouriteState
import androidx.lifecycle.viewModelScope
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase,
    private val getFavoriteMoviesUseCase: GetFavouriteMoviesUseCase
) : BaseViewModel<
    FavouriteState, FavouriteEvent, FavouriteSideEffect
>(FavouriteState()) {

    init {
        getMovies()
    }

    override fun onEvent(event: FavouriteEvent) {
        when (event) {
            is FavouriteEvent.OnMovieClick -> emit(
                FavouriteSideEffect.NavigateToDetail(event.movieId, event.category)
            )
            is FavouriteEvent.OnRemoveFavorite -> removeFavorite(event.movie)
            is FavouriteEvent.OnHomeClick -> emit(FavouriteSideEffect.NavigateToHome)
        }
    }

    private fun removeFavorite(movie: PopularMovie) {
        viewModelScope.launch { toggleFavoriteUseCase(movie) }
    }

    private fun getMovies(){
        viewModelScope.launch {
            getFavoriteMoviesUseCase()
                .distinctUntilChanged()
                .collect { movies ->
                    update { copy(favoriteMovies = movies) }
                }
        }
    }
}