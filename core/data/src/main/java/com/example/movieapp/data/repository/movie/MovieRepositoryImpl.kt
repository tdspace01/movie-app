package com.example.movieapp.data.repository.movie

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.common.resource.asResource
import com.example.movieapp.data.local.dao.FavouriteMovieDao
import com.example.movieapp.data.local.mapper.toDomain
import com.example.movieapp.data.local.mapper.toEntity
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.data.remote.network.movie.MovieApi
import com.example.movieapp.domain.model.movie.Movie
import com.example.movieapp.domain.repository.movie.MovieRepository
import com.example.movieapp.network.apicall.apiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val popularMovieApi: MovieApi,
    private val favoriteMovieDao: FavouriteMovieDao
) : MovieRepository {

    override fun getMovies(): Flow<Resource<List<Movie>>> {
        return apiCall { popularMovieApi.getPopularMovies(page = 1) }
            .asResource { apiResponse ->
                apiResponse.results.map { dto -> dto.toDomain() }
            }
    }

    override suspend fun insertFavourite(movie: Movie) {
        favoriteMovieDao.insertFavourite(movie.toEntity())
    }

    override suspend fun deleteFavourite(movie: Movie) {
        favoriteMovieDao.deleteFavourite(movie.toEntity())
    }

    override fun getAllFavourites(): Flow<List<Movie>> {
        return favoriteMovieDao.getAllFavourites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun isMovieFavourite(movieId: Int): Flow<Boolean> {
        return favoriteMovieDao.isMovieFavourite(movieId)
    }
}