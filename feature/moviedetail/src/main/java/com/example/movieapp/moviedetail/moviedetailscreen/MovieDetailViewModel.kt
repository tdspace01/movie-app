package com.example.movieapp.moviedetail.moviedetailscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movieapp.common.networkstatus.NetworkStatus
import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.common.resource.collectAsResource
import com.example.movieapp.domain.usecase.movie.GetMovieDetailsUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.network.ObserveNetworkStatusUseCase
import com.example.movieapp.navigation.moviedetail.MovieDetailRoute
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MovieDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase
) : BaseViewModel<MovieDetailState, MovieDetailEvent, MovieDetailSideEffect>(MovieDetailState()) {

    private val route = savedStateHandle.toRoute<MovieDetailRoute.MovieDetail>()
    private val movieId = route.movieId
    private val category = route.category
    private var detailsJob: Job? = null
    private var networkJob: Job? = null

    init {
        onEvent(MovieDetailEvent.LoadMovieDetails)
        observeNetworkStatus()
    }

    override fun onEvent(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.LoadMovieDetails -> loadMovieDetails()
            is MovieDetailEvent.OnBackClick -> handleBackClick()
            is MovieDetailEvent.OnToggleFavorite -> handleToggleFavorite()
            is MovieDetailEvent.OnRefresh -> handleRefresh()
        }
    }

    private fun loadMovieDetails() {
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch {
            getMovieDetailsUseCase(movieId).collectAsResource(
                onLoading = { loading -> updateState { copy(isLoading = loading) } },
                onError = { error -> updateState { copy(isLoading = false, errorType = error) } },
                onSuccess = { data -> updateState { copy(isLoading = false, movieDetail = data, errorType = null) } }
            )
        }
    }

    private fun observeNetworkStatus() {
        networkJob?.cancel()
        networkJob = viewModelScope.launch {
            observeNetworkStatusUseCase()
                .distinctUntilChanged()
                .collectLatest { status ->
                    when (status) {
                        is NetworkStatus.Available -> {}
                        is NetworkStatus.Unavailable -> {
                            detailsJob?.cancel()
                            updateState {
                                copy(
                                    isLoading = false,
                                    errorType = NetworkError.NO_INTERNET
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun handleBackClick() {
        emitSideEffect(MovieDetailSideEffect.NavigateBack)
    }

    private fun handleToggleFavorite() {
        if (currentState.errorType == NetworkError.NO_INTERNET) return
        val current = currentState.movieDetail ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(current, category)
        }
    }

    private fun handleRefresh() {
        updateState { copy(errorType = null, isLoading = true) }
        viewModelScope.launch {
            delay(2000.milliseconds)
            loadMovieDetails()
        }
    }
}