package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.domain.model.movie.Movie
import com.example.movieapp.domain.repository.movie.MovieRepository
import kotlinx.coroutines.flow.first

class ToggleFavouriteUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie){
        val isCurrentlyFav = repository.isMovieFavourite(movie.id).first()
        if(isCurrentlyFav){
            repository.deleteFavourite(movie)
        }else{
            repository.insertFavourite(movie)
        }
    }
}