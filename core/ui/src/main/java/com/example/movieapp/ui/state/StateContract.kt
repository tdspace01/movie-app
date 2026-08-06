package com.example.movieapp.ui.state

interface OfflineCapable {
    val isOffline: Boolean
}

interface Refreshable {
    val isRefreshing: Boolean
}

interface ErrorCapable : Refreshable {
    val showErrorScreen: Boolean

    val showFullError: Boolean
        get() = showErrorScreen && !isRefreshing
}