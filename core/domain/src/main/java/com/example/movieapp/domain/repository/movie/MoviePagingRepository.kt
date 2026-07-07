package com.example.movieapp.domain.repository.movie

import androidx.paging.PagingData
import com.example.movieapp.domain.model.movie.PopularMovie
import kotlinx.coroutines.flow.Flow

interface MoviePagingRepository {
    fun getPopularMoviesPaged(): Flow<PagingData<PopularMovie>>
    fun searchMoviesPaged(query: String): Flow<PagingData<PopularMovie>>
    fun getMoviesByGenrePaged(genreId: Int): Flow<PagingData<PopularMovie>>
}