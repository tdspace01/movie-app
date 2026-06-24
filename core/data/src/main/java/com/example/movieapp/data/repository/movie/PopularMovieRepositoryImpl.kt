package com.example.movieapp.data.repository.movie

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.local.dao.FavouriteMovieDao
import com.example.movieapp.data.local.mapper.toDomain
import com.example.movieapp.data.local.mapper.toEntity
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.data.remote.network.movie.PopularMovieApi
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.repository.movie.PopularMovieRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PopularMovieRepositoryImpl(
    private val popularMovieApi: PopularMovieApi,
    private val favoriteMovieDao: FavouriteMovieDao
) : PopularMovieRepository {

    override fun getMovies(): Flow<Resource<List<PopularMovie>>> {
        return apiCall { popularMovieApi.getPopularMovies(page = 1) }
            .asResource { apiResponse ->
                apiResponse.results.map { dto -> dto.toDomain() }
            }
    }

    override suspend fun insertFavourite(movie: PopularMovie) {
        favoriteMovieDao.insertFavourite(movie.toEntity())
    }

    override suspend fun deleteFavourite(movie: PopularMovie) {
        favoriteMovieDao.deleteFavourite(movie.toEntity())
    }

    override fun getAllFavourites(): Flow<List<PopularMovie>> {
        return favoriteMovieDao.getAllFavourites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun isMovieFavourite(movieId: Int): Flow<Boolean> {
        return favoriteMovieDao.isMovieFavourite(movieId)
    }
}