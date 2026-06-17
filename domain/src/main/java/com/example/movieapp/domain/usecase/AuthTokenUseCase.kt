package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.repository.AuthTokenRepository
import kotlinx.coroutines.flow.Flow

class AuthTokenUseCase(
    private val repository: AuthTokenRepository
){
    suspend fun save(token: String){
        repository.saveToken(token)
    }

    fun get(): Flow<String?> = repository.getToken()

    suspend fun clear(){
        repository.clearToken()
    }
}