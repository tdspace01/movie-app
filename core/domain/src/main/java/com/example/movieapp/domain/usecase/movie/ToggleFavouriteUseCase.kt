package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.PopularMovieRepository
import kotlinx.coroutines.flow.first

class ToggleFavouriteUseCase(
    private val repository: PopularMovieRepository
) {
    suspend operator fun invoke(movie: PopularMovie){
        val isCurrentlyFav = repository.isMovieFavourite(movie.id).first()
        if(isCurrentlyFav){
            repository.deleteFavourite(movie)
        }else{
            repository.insertFavourite(movie)
        }
    }
}