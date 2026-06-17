package com.example.movieapp.common.network_status

sealed class NetworkStatus{
    object Available: NetworkStatus()
    object Unavailable: NetworkStatus()
}
