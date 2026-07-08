package com.example.movieapp.home.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.common.networkstatus.NetworkStatus
import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.common.resource.collectAsResource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.usecase.movie.GetPopularMoviesPagedUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.network.ObserveNetworkStatusUseCase
import com.example.movieapp.domain.usecase.search.GetGenresUseCase
import com.example.movieapp.domain.usecase.search.GetMoviesByGenrePagedUseCase
import com.example.movieapp.domain.usecase.search.SearchMoviesPagedUseCase
import com.example.movieapp.home.home.move_mode.MovieListMode
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel(
    private val getPopularMoviesPagedUseCase: GetPopularMoviesPagedUseCase,
    private val searchMoviesPagedUseCase: SearchMoviesPagedUseCase,
    private val getMoviesByGenrePagedUseCase: GetMoviesByGenrePagedUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(HomeState()) {

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedMovies: Flow<PagingData<PopularMovie>> = state
        .map { it.listMode to it.refreshKey }
        .distinctUntilChanged()
        .mapNotNull { (mode, _) -> mode }
        .flatMapLatest { mode ->
            when (mode) {
                MovieListMode.Popular -> getPopularMoviesPagedUseCase(viewModelScope)
                is MovieListMode.Search -> searchMoviesPagedUseCase(mode.query, viewModelScope)
                is MovieListMode.ByGenre -> {
                    getMoviesByGenrePagedUseCase(mode.genreId, viewModelScope)
                }
            }
        }.cachedIn(viewModelScope)

    init {
        loadGenres()
        observeDebouncedSearch()
        observeNetwork()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnSearchQueryChanged -> updateState {
                if (event.query.isNotBlank()) {
                    copy(
                        searchQuery = event.query,
                        isGenresExpanded = false,
                        selectedGenreId = null,
                    )
                } else {
                    copy(searchQuery = event.query)
                }
            }

            HomeEvent.OnClearSearch ->
                updateState { copy(searchQuery = "", activeSearchQuery = "") }

            HomeEvent.OnToggleGenresVisibility ->
                updateState { copy(isGenresExpanded = !isGenresExpanded) }

            is HomeEvent.OnGenreSelected ->
                if (currentState.errorType != NetworkError.NO_INTERNET) {
                updateState {
                    copy(
                        selectedGenreId = if (selectedGenreId == event.genreId) null
                            else event.genreId
                    )
                }
            }

            HomeEvent.OnGenreCleared -> if (currentState.errorType != NetworkError.NO_INTERNET) {
                updateState { copy(selectedGenreId = null) }
            }

            is HomeEvent.OnToggleFavorite ->
                viewModelScope.launch { toggleFavoriteUseCase(event.movie) }

            is HomeEvent.OnMovieClick ->
                emitSideEffect(HomeSideEffect.NavigateToDetail(event.movieId, event.category))

            HomeEvent.OnFavoriteClick ->
                emitSideEffect(HomeSideEffect.NavigateToFavorite)

            HomeEvent.OnRefresh -> refresh()
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeDebouncedSearch() {
        viewModelScope.launch {
            state.map { it.searchQuery }
                .distinctUntilChanged()
                .drop(1)
                .debounce(700.milliseconds)
                .collect { query ->
                    if (currentState.errorType == NetworkError.NO_INTERNET) return@collect
                    updateState { copy(activeSearchQuery = query) }
                }
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            observeNetworkStatusUseCase()
                .distinctUntilChanged()
                .collect { status ->
                    when (status) {
                        NetworkStatus.Available -> {
                            if (currentState.errorType == NetworkError.NO_INTERNET) refresh()
                        }
                        NetworkStatus.Unavailable -> {
                            updateState { copy(errorType = NetworkError.NO_INTERNET) }
                        }
                    }
                }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase(Unit).collectAsResource(
                onError = { error -> updateState { copy(errorType = error) } },
                onSuccess = { genres -> updateState { copy(genres = genres) } },
            )
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            if (!observeNetworkStatusUseCase.isConnected()) return@launch
            updateState { copy(errorType = null, refreshKey = refreshKey + 1) }
            if (currentState.genres.isEmpty()) loadGenres()
        }
    }
}