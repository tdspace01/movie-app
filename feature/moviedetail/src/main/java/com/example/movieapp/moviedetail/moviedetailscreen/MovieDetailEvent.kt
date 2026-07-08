package com.example.movieapp.moviedetail.moviedetailscreen

sealed interface MovieDetailEvent {
    data object OnRefresh : MovieDetailEvent
    data object OnBackClick : MovieDetailEvent
    data object OnToggleFavorite : MovieDetailEvent
}