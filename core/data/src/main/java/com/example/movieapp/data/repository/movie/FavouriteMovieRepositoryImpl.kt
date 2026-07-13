package com.example.movieapp.data.repository.movie

import com.example.movieapp.data.local.mapper.toDomain
import com.example.movieapp.data.local.mapper.toEntity
import com.example.movieapp.data.remote.datasource.local.PopularMovieLocalDataSourceImpl
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouriteMovieRepositoryImpl(
    private val localDataSource: PopularMovieLocalDataSourceImpl
) : FavouriteMovieRepository {
    override suspend fun insertFavourite(movie: PopularMovie) {
        localDataSource.insertFavourite(movie.toEntity())
    }

    override suspend fun deleteFavourite(movie: PopularMovie) {
        localDataSource.deleteFavourite(movie.toEntity())
    }

    override fun getAllFavourites(): Flow<List<PopularMovie>> {
        return localDataSource.getAllFavourites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun isMovieFavourite(movieId: Int): Flow<Boolean> {
        return localDataSource.isMovieFavourite(movieId)
    }

    override fun getFavouriteIds(): Flow<Set<Int>> {
        return localDataSource.getAllFavourites().map { entities ->
            entities.map { it.id }.toSet()
        }
    }
}