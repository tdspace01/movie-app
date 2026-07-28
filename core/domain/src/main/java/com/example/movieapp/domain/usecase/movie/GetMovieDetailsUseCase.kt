package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.domain.repository.movie.MovieDetailRepository
import com.example.movieapp.domain.usecase.common.BaseUseCase
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(
    private val repository: MovieDetailRepository
) : BaseUseCase<Int, MovieDetail>() {
    override fun invoke(params: Int): Flow<Resource<MovieDetail>> {
        return repository.getMovieDetails(movieId = params)
    }
}