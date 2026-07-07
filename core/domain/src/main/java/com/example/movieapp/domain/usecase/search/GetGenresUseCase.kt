package com.example.movieapp.domain.usecase.search

import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.domain.model.search.Genre
import com.example.movieapp.domain.repository.search.GenreRepository
import com.example.movieapp.domain.usecase.common.BaseUseCase
import kotlinx.coroutines.flow.Flow

class GetGenresUseCase(
    private val repository: GenreRepository
) : BaseUseCase<Unit, List<Genre>>() {
    override fun invoke(params: Unit): Flow<NetworkResource<List<Genre>>> {
        return repository.getGenres()
    }
}