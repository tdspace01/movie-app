package com.example.movieapp.home.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.common.networkstatus.NetworkStatus
import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.common.resource.collectAsResource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.usecase.common.withFavouriteState
import com.example.movieapp.domain.usecase.movie.GetPopularMoviesPagedUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.network.ObserveNetworkStatusUseCase
import com.example.movieapp.domain.usecase.search.GetFavouriteIdsUseCase
import com.example.movieapp.domain.usecase.search.GetGenresUseCase
import com.example.movieapp.domain.usecase.search.GetMoviesByGenrePagedUseCase
import com.example.movieapp.domain.usecase.search.SearchMoviesPagedUseCase
import com.example.movieapp.home.home.move_mode.MovieListMode
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel(
    private val getPopularMoviesPagedUseCase: GetPopularMoviesPagedUseCase,
    private val searchMoviesPagedUseCase: SearchMoviesPagedUseCase,
    private val getMoviesByGenrePagedUseCase: GetMoviesByGenrePagedUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getFavouriteIdsUseCase: GetFavouriteIdsUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(HomeState()) {

    private val currentMode = MutableStateFlow<MovieListMode?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val pagedMoviesBase: Flow<PagingData<PopularMovie>> = currentMode
        .filterNotNull()
        .flatMapLatest { mode ->
            when (mode) {
                is MovieListMode.Popular -> getPopularMoviesPagedUseCase()
                is MovieListMode.Search -> searchMoviesPagedUseCase(mode.query)
                is MovieListMode.ByGenre -> getMoviesByGenrePagedUseCase(mode.genreId)
            }
        }
        .cachedIn(viewModelScope)

    val pagedMovies: Flow<PagingData<PopularMovie>> =
        pagedMoviesBase.withFavouriteState(getFavouriteIdsUseCase())

    init {
        loadGenres()
        observeSearchQueryAndFetch()
        observeNetworkStatus()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnClearSearch -> handleClearSearch()
            is HomeEvent.OnSearchQueryChanged -> handleSearchQueryChanged(event.query)
            is HomeEvent.OnFavoriteClick -> handleFavoriteClick()
            is HomeEvent.OnToggleGenresVisibility -> handleToggleGenresVisibility()
            is HomeEvent.OnGenreCleared -> handleGenreCleared()
            is HomeEvent.OnMovieClick -> handleMovieClick(event.movieId, event.category)
            is HomeEvent.OnToggleFavorite -> handleToggleFavorite(event.movie)
            is HomeEvent.OnRefresh -> handleRefresh()
            is HomeEvent.OnGenreSelected -> handleGenreSelected(event.genreId)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQueryAndFetch() {
        viewModelScope.launch {
            state.map { it.searchQuery }
                .distinctUntilChanged()
                .drop(1)
                .debounce(700L.milliseconds)
                .collectLatest { query ->
                    if (currentState.errorType == NetworkError.NO_INTERNET) return@collectLatest
                    currentMode.value = when {
                        query.isNotBlank() -> MovieListMode.Search(query)
                        currentState.selectedGenreId != null ->
                            MovieListMode.ByGenre(currentState.selectedGenreId!!)
                        else -> MovieListMode.Popular
                    }
                }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase(Unit).collectAsResource(
                onLoading = { loading -> updateState { copy(isGenresLoading = loading) } },
                onError = { error -> updateState { copy(errorType = error) } },
                onSuccess = { data ->
                    updateState { copy(genres = data) }
                    if (currentState.errorType != NetworkError.NO_INTERNET
                        && currentMode.value == null) {
                        currentMode.value = MovieListMode.Popular
                    }
                }
            )
        }
    }

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            observeNetworkStatusUseCase()
                .distinctUntilChanged()
                .collectLatest { status ->
                    when (status) {
                        is NetworkStatus.Available -> {
                            if (currentState.errorType == NetworkError.NO_INTERNET) {
                                doRefresh()
                            }
                        }
                        is NetworkStatus.Unavailable -> {
                            updateState { copy(errorType = NetworkError.NO_INTERNET) }
                        }
                    }
                }
        }
    }

    private fun handleClearSearch() {
        updateState { copy(searchQuery = "") }
    }

    private fun handleSearchQueryChanged(query: String) {
        updateState { copy(searchQuery = query) }
    }

    private fun handleFavoriteClick() {
        emitSideEffect(HomeSideEffect.NavigateToFavorite)
    }

    private fun handleToggleGenresVisibility() {
        updateState { copy(isGenresVisible = !currentState.isGenresVisible) }
    }

    private fun handleGenreCleared() {
        updateState { copy(selectedGenreId = null) }
        if (currentState.errorType != NetworkError.NO_INTERNET) {
            currentMode.value = MovieListMode.Popular
        }
    }

    private fun handleMovieClick(movieId: Int, category: String) {
        emitSideEffect(HomeSideEffect.NavigateToDetail(movieId = movieId, category = category))
    }

    private fun handleToggleFavorite(movie: PopularMovie) {
        viewModelScope.launch { toggleFavoriteUseCase(movie) }
    }

    private fun handleRefresh() {
        viewModelScope.launch {
            if (!observeNetworkStatusUseCase.isConnected()) return@launch
            doRefresh()
        }
    }

    private fun doRefresh() {
        updateState { copy(errorType = null) }
        if (currentState.genres.isEmpty()) {
            loadGenres()
        } else {
            val mode = when {
                currentState.searchQuery.isNotBlank() ->
                    MovieListMode.Search(currentState.searchQuery)
                currentState.selectedGenreId != null ->
                    MovieListMode.ByGenre(currentState.selectedGenreId!!)
                else -> MovieListMode.Popular
            }
            currentMode.value = null
            currentMode.value = mode
        }
    }

    private fun handleGenreSelected(genreId: Int) {
        val newGenreId = if (currentState.selectedGenreId == genreId) null else genreId
        updateState { copy(selectedGenreId = newGenreId) }
        if (currentState.errorType == NetworkError.NO_INTERNET) return
        currentMode.value = if (newGenreId != null) MovieListMode.ByGenre(newGenreId)
            else MovieListMode.Popular
    }
}