package com.example.movieapp.moviedetail.moviedetailscreen

sealed interface MovieDetailSideEffect{
    object NavigateBack: MovieDetailSideEffect
}