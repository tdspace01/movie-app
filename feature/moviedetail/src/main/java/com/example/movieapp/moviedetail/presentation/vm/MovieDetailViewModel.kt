package com.example.movieapp.moviedetail.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movieapp.common.resource.collectAsResource
import com.example.movieapp.domain.usecase.movie.GetMovieDetailsUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.network.ObserveNetworkStatusUseCase
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailEvent
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailSideEffect
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailState
import com.example.movieapp.navigation.moviedetail.MovieDetailRoute
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
) : BaseViewModel<
        MovieDetailState, MovieDetailEvent, MovieDetailSideEffect
        >(MovieDetailState()) {

    private val args = savedStateHandle.toRoute<MovieDetailRoute.MovieDetail>()
    private val refreshTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    init {
        observeNetwork()
        observeDetails()
    }

    override fun onEvent(event: MovieDetailEvent) {
        when (event) {
            MovieDetailEvent.OnRefresh -> reload()
            MovieDetailEvent.OnToggleFavorite -> toggleFavorite()
            MovieDetailEvent.OnBackClick -> emit(MovieDetailSideEffect.NavigateBack)
        }
    }

    private fun observeNetwork(){
        observeNetwork(
            networkStatus = observeNetworkStatusUseCase(),
            onAvailable = { copy(isOffline = false) },
            onUnavailable = {
                if (movieDetail == null) {
                    copy(
                        isOffline = true,isLoading = false,
                        isRefreshing = false,showErrorScreen = true
                    )
                } else {
                    copy(isOffline = true)
                }
            },
        )
    }

    private fun toggleFavorite() {
        requireOnline {
            val movie = currentState.movieDetail ?: return@requireOnline
            viewModelScope.launch { toggleFavoriteUseCase(movie, args.category) }
        }
    }

    private fun observeDetails() {
        viewModelScope.launch {
            getMovieDetailsUseCase(args.movieId, refreshTrigger)
                .collectAsResource(
                    onLoading = { loading ->
                        if (!currentState.isRefreshing) {
                            update { copy(isLoading = loading) }
                        }
                    },
                    onError = {
                        update {
                            copy(isLoading = false, isRefreshing = false, showErrorScreen = true)
                        }
                    },
                    onSuccess = { data ->
                        update {
                            copy(
                                isLoading = false,isRefreshing = false,
                                movieDetail = data,showErrorScreen = false
                            )
                        }
                    }
                )
        }
    }

    private fun reload() {
        refresh(
            isRefreshing = { currentState.isRefreshing },
            isConnected = observeNetworkStatusUseCase::isConnected,
            onStart = { copy(isRefreshing = true, showErrorScreen = false) },
            onOffline = {
                copy(isRefreshing = false, isOffline = true, showErrorScreen = true)
            },
            onOnline = { copy(isRefreshing = false, isOffline = false, showErrorScreen = false) },
            onConnected = { refreshTrigger.emit(Unit) }
        )
    }
}