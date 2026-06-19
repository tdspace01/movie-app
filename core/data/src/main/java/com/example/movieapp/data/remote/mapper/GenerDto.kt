package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.model.request_dto.GenreRequestDto
import com.example.movieapp.domain.model.Genre

fun GenreRequestDto.toDomain(): Genre{
    return Genre(
        id = this.id,
        name = this.name
    )
}