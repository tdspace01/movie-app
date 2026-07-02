package com.example.movieapp.moviedetail.moviedetailscreen

sealed interface MovieDetailEvent {
    object OnRefresh: MovieDetailEvent
    object OnBackClick: MovieDetailEvent
    object LoadMovieDetails: MovieDetailEvent
    data object OnToggleFavorite : MovieDetailEvent
}