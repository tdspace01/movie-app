package com.example.movieapp.network.networkobserver

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import com.example.movieapp.common.networkstatus.NetworkStatus
import com.example.movieapp.domain.repository.network.NetworkObserver
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class NetworkObserverImpl(
    context: Context,
) : NetworkObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun observe(): Flow<NetworkStatus> = callbackFlow {
        val networksWithInternet = linkedSetOf<Network>()

        fun syncNetwork(network: Network) {
            val caps = connectivityManager.getNetworkCapabilities(network)
            if (caps != null && hasInternetCapability(caps)) {
                networksWithInternet.add(network)
            } else {
                networksWithInternet.remove(network)
            }
        }

        fun emitCurrentStatus() {
            val status = if (networksWithInternet.isNotEmpty()) {
                NetworkStatus.Available
            } else {
                NetworkStatus.Unavailable
            }
            trySend(status)
        }

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                syncNetwork(network)
                emitCurrentStatus()
            }

            override fun onLost(network: Network) {
                networksWithInternet.remove(network)
                emitCurrentStatus()
            }

            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                syncNetwork(network)
                emitCurrentStatus()
            }
        }

        connectivityManager.activeNetwork?.let(::syncNetwork)
        emitCurrentStatus()

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
        .buffer(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .distinctUntilChanged()

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun isConnected(): Boolean = hasInternetConnection()

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun hasInternetConnection(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val caps = connectivityManager.getNetworkCapabilities(network) ?: return false
        return hasInternetCapability(caps)
    }

    private fun hasInternetCapability(caps: NetworkCapabilities): Boolean {
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}