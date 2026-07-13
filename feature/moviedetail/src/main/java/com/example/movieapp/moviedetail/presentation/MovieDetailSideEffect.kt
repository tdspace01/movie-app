package com.example.movieapp.moviedetail.presentation

sealed interface MovieDetailSideEffect {
    data object NavigateBack : MovieDetailSideEffect
}