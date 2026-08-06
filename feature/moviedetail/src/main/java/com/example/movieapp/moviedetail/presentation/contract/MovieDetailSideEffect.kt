package com.example.movieapp.moviedetail.presentation.contract

sealed interface MovieDetailSideEffect {
    data object NavigateBack : MovieDetailSideEffect
}