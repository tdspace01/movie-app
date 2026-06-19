package com.example.movieapp.domain.repository

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.PopularMovie
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenres(): Flow<Resource<List<Genre>>>
    fun getMoviesByGenre(genreId:Int):Flow<Resource<List<PopularMovie>>>
}