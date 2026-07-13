package com.example.movieapp.moviedetail.presentation

import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.domain.model.movie.MovieDetail

data class MovieDetailState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorType: NetworkError? = null,
    val movieDetail: MovieDetail? = null
)