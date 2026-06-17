package com.example.movieapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthTokenRepository{
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String?>
    suspend fun clearToken()
}