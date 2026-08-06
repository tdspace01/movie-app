package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.search.GenreListResponseDto
import com.example.movieapp.domain.model.search.Genre

fun GenreListResponseDto.GenreDto.toDomain(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}