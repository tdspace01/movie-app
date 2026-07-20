package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.request_dto.search.GenreDto
import com.example.movieapp.domain.model.search.Genre

fun GenreDto.toDomain(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}