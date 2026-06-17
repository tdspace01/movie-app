package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.request_dto.MovieDto
import com.example.movieapp.domain.model.Movie

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = this.id,
    )
}