package com.example.movieapp.domain.usecase.search

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.domain.repository.search.GenreRepository
import com.example.movieapp.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class GetGenresUseCase(
    private val repository: GenreRepository
): BaseUseCase<Nothing,List<Genre>>() {

    override fun invoke(params: Nothing?): Flow<Resource<List<Genre>>> {
        return repository.getGenres()
    }
}