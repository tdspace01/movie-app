package com.example.movieapp.home.home.move_mode

sealed interface MovieListMode {
    object Popular : MovieListMode
    data class Search(val query: String) : MovieListMode
    data class ByGenre(val genreId: Int) : MovieListMode
}