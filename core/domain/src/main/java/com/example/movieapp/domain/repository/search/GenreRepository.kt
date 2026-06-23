package com.example.movieapp.domain.repository.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.movie.Movie
import com.example.movieapp.domain.model.search.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenres():Flow<Resource<List<Genre>>>
    fun getMoviesByGenre(genreId:Int): Flow<Resource<List<Movie>>>
}