package com.example.movieapp.domain.usecase.network

import com.example.movieapp.common.networkstatus.NetworkStatus
import com.example.movieapp.domain.repository.network.NetworkObserver
import kotlinx.coroutines.flow.Flow

class ObserveNetworkStatusUseCase(
    private val networkObserver: NetworkObserver
) {
    operator fun invoke(): Flow<NetworkStatus> = networkObserver.observe()
    suspend fun isConnected(): Boolean = networkObserver.isConnected()
}