package com.example.movieapp.home.presentation.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.common.resource.collectAsResource
import com.example.movieapp.domain.model.movie.HomeMovieFilter
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.usecase.common.withPagingFavouriteState
import com.example.movieapp.domain.usecase.movie.GetFavouriteIdsUseCase
import com.example.movieapp.domain.usecase.movie.ObserveHomeMoviesUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.network.ObserveNetworkStatusUseCase
import com.example.movieapp.domain.usecase.search.GetGenresUseCase
import com.example.movieapp.home.presentation.contract.HomeEvent
import com.example.movieapp.home.presentation.contract.HomeSideEffect
import com.example.movieapp.home.presentation.contract.HomeState
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel(
    getFavouriteIdsUseCase: GetFavouriteIdsUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    observeHomeMoviesUseCase: ObserveHomeMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(HomeState()) {

    private val refreshTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val pagedMovies: Flow<PagingData<PopularMovie>> = observeHomeMoviesUseCase(
        filter = state.map { HomeMovieFilter(it.activeSearchQuery, it.selectedGenreId) },
        refreshTrigger = refreshTrigger,
    ).cachedIn(viewModelScope).withPagingFavouriteState(getFavouriteIdsUseCase())

    init {
        observeNetwork()
        loadGenres()
        observeSearchQuery()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnToggleGenresVisibility -> update {
                copy(isGenresExpanded = !isGenresExpanded)
            }
            is HomeEvent.OnToggleFavorite -> {
                viewModelScope.launch { toggleFavoriteUseCase(event.movie) }
            }
            is HomeEvent.OnMovieClick -> emit(
                HomeSideEffect.NavigateToDetail(event.movieId, event.category)
            )
            is HomeEvent.OnFavoriteClick -> {
                emit(HomeSideEffect.NavigateToFavorite)
            }
            is HomeEvent.OnMoviesLoaded -> update {
                copy(hasLoadedContent = true, showErrorScreen = false)
            }
            is HomeEvent.OnRefresh -> refresh()
            is HomeEvent.OnGenreCleared -> clearGenre()
            is HomeEvent.OnGenreSelected -> selectGenre(event.genreId)
            is HomeEvent.OnSearchQueryChanged -> updateSearchQuery(event.query)
        }
    }

    private fun observeNetwork(){
        observeNetwork(
            networkStatus = observeNetworkStatusUseCase(),
            onAvailable = { copy(isOffline = false) },
            onUnavailable = { copy(isOffline = true, showErrorScreen = !hasLoadedContent) },
        )
    }
    private fun updateSearchQuery(query: String) {
        update {
            if (query.isBlank()) {
                copy(searchQuery = query, activeSearchQuery = "")
            } else {
                copy(searchQuery = query, isGenresExpanded = false, selectedGenreId = null)
            }
        }
    }

    private fun selectGenre(genreId: Int) {
        requireOnline {
            update { copy(selectedGenreId = if (selectedGenreId == genreId) null else genreId) }
        }
    }

    private fun clearGenre() {
        requireOnline { update { copy(selectedGenreId = null) } }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            state.map { it.searchQuery }
                .distinctUntilChanged()
                .drop(1)
                .debounce(700.milliseconds)
                .collect { query ->
                    if (!currentState.isOffline) {
                        update { copy(activeSearchQuery = query) }
                    }
                }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase(Unit).collectAsResource(
                onError = { error ->
                    if (error != NetworkError.NO_INTERNET) {
                        update { copy(showErrorScreen = true) }
                    }
                },
                onSuccess = { genres ->
                    update { copy(genres = genres) }
                    refreshTrigger.tryEmit(Unit)
                },
            )
        }
    }

    private fun refresh() {
        refresh(
            isRefreshing = { currentState.isRefreshing },
            isConnected = observeNetworkStatusUseCase::isConnected,
            onStart = { copy(isRefreshing = true, showErrorScreen = false) },
            onOffline = {
                copy(isRefreshing = false, isOffline = true, showErrorScreen = !hasLoadedContent)
            },
            onOnline = { copy(isRefreshing = false, isOffline = false, showErrorScreen = false) },
            onConnected = {
                if (currentState.genres.isEmpty()) { loadGenres() }
                else { refreshTrigger.emit(Unit) }
            },
        )
    }
}