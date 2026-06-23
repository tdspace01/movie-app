package com.example.movieapp.domain.usecase.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.domain.repository.search.GenreRepository
import kotlinx.coroutines.flow.Flow

class GetGenresUseCase(
    private val repository: GenreRepository
) : BaseNoParamUseCase<List<Genre>>() {
    override operator fun invoke(): Flow<Resource<List<Genre>>> {
        return repository.getGenres()
    }
}