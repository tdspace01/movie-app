package com.example.movieapp.data.local.mapper

import com.example.movieapp.data.local.entity.FavouriteMovieEntity
import com.example.movieapp.domain.model.movie.PopularMovie

fun FavouriteMovieEntity.toDomain(): PopularMovie {
    return PopularMovie(
        id = this.id,
        title = this.title,
        posterUrl = this.posterUrl,
        year = this.releaseYear,
        category = this.category
    )
}

fun PopularMovie.toEntity(): FavouriteMovieEntity {
    return FavouriteMovieEntity(
        id = this.id,
        title = this.title,
        posterUrl = this.posterUrl,
        releaseYear = this.year,
        category = this.category,
        addedAt = System.currentTimeMillis()
    )
}