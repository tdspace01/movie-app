package com.example.movieapp.domain.repository

import com.example.movieapp.common.network_status.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface NetworkObserver{
    fun observe(): Flow<NetworkStatus>
    suspend fun isConnected(): Boolean
}