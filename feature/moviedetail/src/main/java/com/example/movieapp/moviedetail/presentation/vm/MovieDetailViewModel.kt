package com.example.movieapp.moviedetail.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movieapp.common.networkstatus.NetworkStatus
import com.example.movieapp.common.resource.collectAsResource
import com.example.movieapp.domain.usecase.movie.GetMovieDetailsUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.network.ObserveNetworkStatusUseCase
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailEvent
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailSideEffect
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailState
import com.example.movieapp.navigation.moviedetail.MovieDetailRoute
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MovieDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
) : BaseViewModel<
        MovieDetailState, MovieDetailEvent, MovieDetailSideEffect
>(MovieDetailState()) {

    private val args = savedStateHandle.toRoute<MovieDetailRoute.MovieDetail>()
    private val reloadTrigger = MutableStateFlow(0)

    init {
        observeDetails()
        observeNetwork()
    }

    override fun onEvent(event: MovieDetailEvent) {
        when (event) {
            MovieDetailEvent.OnRefresh -> reload()
            MovieDetailEvent.OnToggleFavorite -> toggleFavorite()
            MovieDetailEvent.OnBackClick -> emitSideEffect(MovieDetailSideEffect.NavigateBack)
        }
    }

    private fun toggleFavorite() {
        if (currentState.isOffline) return
        val movie = currentState.movieDetail ?: return
        viewModelScope.launch { toggleFavoriteUseCase(movie, args.category) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeDetails() {
        viewModelScope.launch {
            reloadTrigger
                .flatMapLatest { getMovieDetailsUseCase(args.movieId) }
                .collectAsResource(
                    onLoading = { loading ->
                        if (!currentState.isRefreshing) {
                            updateState { copy(isLoading = loading) }
                        }
                    },
                    onError = {
                        updateState {
                            copy(isLoading = false,isRefreshing = false,showErrorScreen = true)
                        }
                    },
                    onSuccess = { data ->
                        updateState {
                            copy(
                                isLoading = false,isRefreshing = false,
                                movieDetail = data,showErrorScreen = false
                            )
                        }
                    }
                )
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            observeNetworkStatusUseCase().collect { status ->
                updateState {
                    when (status) {
                        NetworkStatus.Available -> copy(isOffline = false)
                        NetworkStatus.Unavailable -> if (movieDetail == null) {
                            copy(
                                isOffline = true,isLoading = false,
                                isRefreshing = false,showErrorScreen = true
                            )
                        } else { copy(isOffline = true) }
                    }
                }
            }
        }
    }

    private fun reload() {
        if (currentState.isRefreshing) return
        viewModelScope.launch {
            updateState { copy(isRefreshing = true, showErrorScreen = false) }
            delay(2.seconds)
            val isConnected = observeNetworkStatusUseCase.isConnected()
            updateState {
                if (!isConnected) {
                    copy(isRefreshing = false, isOffline = true, showErrorScreen = true)
                } else {
                    copy(isRefreshing = false, isOffline = false, showErrorScreen = false)
                }
            }
            if (isConnected) reloadTrigger.value++
        }
    }
}