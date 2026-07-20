package com.example.movieapp.moviedetail.presentation.contract

import com.example.movieapp.domain.model.movie.MovieDetail

data class MovieDetailState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isOffline: Boolean = false,
    val showErrorScreen: Boolean = false,
) {
    val showFullError: Boolean
        get() = showErrorScreen && !isRefreshing

    val showContent: Boolean
        get() = movieDetail != null && !showFullError && !isLoading && !isRefreshing
}