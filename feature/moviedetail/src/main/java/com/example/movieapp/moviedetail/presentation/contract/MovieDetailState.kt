package com.example.movieapp.moviedetail.presentation.contract

import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.ui.state.ErrorCapable
import com.example.movieapp.ui.state.OfflineCapable

data class MovieDetailState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isOffline: Boolean = false,
    override val showErrorScreen: Boolean = false,
) : OfflineCapable, ErrorCapable {
    val showContent: Boolean
        get() = movieDetail != null && !showFullError && !isLoading && !isRefreshing
}