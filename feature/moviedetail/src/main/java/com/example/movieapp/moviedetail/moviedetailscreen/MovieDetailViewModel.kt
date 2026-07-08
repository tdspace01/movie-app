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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
) : BaseViewModel
<MovieDetailState, MovieDetailEvent, MovieDetailSideEffect>(MovieDetailState()) {

    private val args = savedStateHandle.toRoute<MovieDetailRoute.MovieDetail>()
    private val reloadTrigger = MutableStateFlow(0)

    init {
        observeDetails()
        observeNetwork()
    }

    override fun onEvent(event: MovieDetailEvent) {
        when (event) {
            MovieDetailEvent.OnBackClick ->
                emitSideEffect(MovieDetailSideEffect.NavigateBack)

            MovieDetailEvent.OnToggleFavorite -> {
                if (currentState.errorType == NetworkError.NO_INTERNET) return
                val movie = currentState.movieDetail ?: return
                viewModelScope.launch { toggleFavoriteUseCase(movie, args.category) }
            }

            MovieDetailEvent.OnRefresh -> reload()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeDetails() {
        viewModelScope.launch {
            reloadTrigger
                .flatMapLatest { getMovieDetailsUseCase(args.movieId) }
                .collectAsResource(
                    onLoading = { loading -> updateState { copy(isLoading = loading) } },
                    onError = { error -> updateState {
                        copy(isLoading = false, errorType = error)
                    } },
                    onSuccess = { data ->
                        updateState {
                            copy(isLoading = false, movieDetail = data, errorType = null)
                        }
                    }
                )
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            observeNetworkStatusUseCase()
                .distinctUntilChanged()
                .collect { status ->
                    when (status) {
                        NetworkStatus.Available -> {
                            if (currentState.errorType == NetworkError.NO_INTERNET) reload()
                        }
                        NetworkStatus.Unavailable -> {
                            updateState {
                                copy(isLoading = false, errorType = NetworkError.NO_INTERNET)
                            }
                        }
                    }
                }
        }
    }

    private fun reload() {
        viewModelScope.launch {
            if (!observeNetworkStatusUseCase.isConnected()) return@launch
            updateState { copy(errorType = null, isLoading = true) }
            reloadTrigger.value++
        }
    }
}