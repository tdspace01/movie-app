package com.example.movieapp.domain.usecase

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow

class GenresUseCase(
    private val repository: GenreRepository
) {
    operator fun invoke(): Flow<Resource<List<Genre>>>{
        return repository.getGenres()
    }
}