package com.example.movieapp.domain.repository.network

import com.example.movieapp.common.networkstatus.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface NetworkObserver {
    fun observe(): Flow<NetworkStatus>
    suspend fun isConnected(): Boolean
}