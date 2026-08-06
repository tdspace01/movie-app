package com.example.movieapp.moviedetail.presentation.contract

sealed interface MovieDetailEvent {
    data object OnRefresh : MovieDetailEvent
    data object OnBackClick : MovieDetailEvent
    data object OnToggleFavorite : MovieDetailEvent
}