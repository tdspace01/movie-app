package com.example.movieapp.home.home

import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.domain.model.movie.PopularMovie

data class HomeState(
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val selectedGenreId: Int? = null,
    val errorType: NetworkError? = null,
    val isGenresVisible: Boolean = false,
    val isGenresLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val popularMovies: List<PopularMovie> = emptyList(),
)