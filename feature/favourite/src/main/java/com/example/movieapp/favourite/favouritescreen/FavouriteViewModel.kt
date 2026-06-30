package com.example.movieapp.favourite.favouritescreen

import com.example.movieapp.domain.usecase.movie.GetFavouriteMoviesUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.ui.base.BaseViewModel

class FavoriteViewModel(
    private val getFavoriteMoviesUseCase: GetFavouriteMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase
) : BaseViewModel<Unit, Unit, Unit>(Unit) {

}
