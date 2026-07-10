package com.example.movieapp.home.home.movie_mode

sealed interface MovieListMode {
    object Popular : MovieListMode
    data class Search(val query: String) : MovieListMode
    data class ByGenre(val genreId: Int) : MovieListMode
}