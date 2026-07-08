package com.example.movieapp.moviedetail.moviedetailscreen

sealed interface MovieDetailSideEffect {
    data object NavigateBack : MovieDetailSideEffect
}