package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import kotlinx.coroutines.flow.first

class ToggleFavouriteUseCase(
    private val repository: FavouriteMovieRepository
) {
    suspend operator fun invoke(movie: PopularMovie) {
        toggle(movie)
    }

    suspend operator fun invoke(movie: MovieDetail, category: String) {
        toggle(
            PopularMovie(
                id = movie.id,
                title = movie.title,
                posterUrl = movie.posterUrl,
                year = movie.releaseYear,
                isFavorite = movie.isFavorite,
                category = category
            )
        )
    }

    private suspend fun toggle(movie: PopularMovie) {
        val isCurrentlyFav = repository.isMovieFavourite(movie.id).first()
        if (isCurrentlyFav) repository.deleteFavourite(movie) else repository.insertFavourite(movie)
    }
}