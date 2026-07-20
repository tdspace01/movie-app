package com.example.movieapp.domain.repository.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.PopularMovie
import kotlinx.coroutines.flow.Flow

interface MoviesByGenreRepository {
    fun getMoviesByGenre(genreId:Int): Flow<Resource<List<PopularMovie>>>
}