package com.example.movieapp.moviedetail.moviedetailscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movieapp.common.resource.collectAsResource
import com.example.movieapp.domain.usecase.movie.GetMovieDetailsUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.navigation.moviedetail.MovieDetailRoute
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MovieDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase
): BaseViewModel<MovieDetailState, MovieDetailEvent, MovieDetailSideEffect>(MovieDetailState()) {

    private val route = savedStateHandle.toRoute<MovieDetailRoute.MovieDetail>()
    private val movieId = route.movieId
    private val category = route.category

    init {
        onEvent(MovieDetailEvent.LoadMovieDetails)
    }

    override fun onEvent(event: MovieDetailEvent) {
        when(event){
            is MovieDetailEvent.LoadMovieDetails -> loadMovieDetails()
            is MovieDetailEvent.OnBackClick -> emitSideEffect(MovieDetailSideEffect.NavigateBack)

            is MovieDetailEvent.OnToggleFavorite -> {
                val current = currentState.movieDetail ?: return
                updateState {
                    copy(movieDetail = current.copy(isFavorite = !current.isFavorite))
                }

                viewModelScope.launch {
                    toggleFavoriteUseCase(current,category)
                }
            }

            is MovieDetailEvent.OnRefresh -> {
                updateState { copy(errorType = null, isLoading = true) }
                viewModelScope.launch {
                    delay(2000.milliseconds)
                    loadMovieDetails()
                }
            }
        }
    }

    private fun loadMovieDetails(){
        viewModelScope.launch {
            getMovieDetailsUseCase(movieId).collectAsResource(
                onLoading = { loading -> updateState { copy(isLoading = loading) } },
                onError = { error -> updateState { copy(errorType = error) } },
                onSuccess = { data -> updateState { copy(movieDetail = data, errorType = null) } }
            )
        }
    }
}