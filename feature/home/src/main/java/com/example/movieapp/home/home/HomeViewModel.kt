package com.example.movieapp.home.home

import androidx.lifecycle.viewModelScope
import com.example.movieapp.common.networkstatus.NetworkStatus
import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.common.resource.collectAsResource
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.domain.usecase.movie.GetPopularMoviesUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.network.ObserveNetworkStatusUseCase
import com.example.movieapp.domain.usecase.search.GetGenresUseCase
import com.example.movieapp.domain.usecase.search.GetMoviesByGenreUseCase
import com.example.movieapp.domain.usecase.search.SearchMoviesUseCase
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel(
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase,
    private val observeNetworkStatusUseCase: ObserveNetworkStatusUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(HomeState()) {

    private var moviesJob: Job? = null
    private var networkJob: Job? = null

    init {
        onEvent(HomeEvent.LoadGenres)
        observeSearchQueryAndFetch()
        observeNetworkStatus()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadGenres -> loadGenres()
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

                    if (query.isNotBlank()) {
                        searchMovies(query)
                    } else {
                        val selectedGenre = currentState.selectedGenreId
                        if (selectedGenre != null) {
                            loadMoviesByGenre(selectedGenre)
                        } else {
                            loadPopularMovies()
                        }
                    }
                }
        }
    }

    private fun loadPopularMovies() {
        moviesJob?.cancel()
        moviesJob = viewModelScope.launch {
            updateState { copy(isLoading = true) }
            delay(1000.milliseconds)

            popularMoviesUseCase().collectAsResource(
                onLoading = { loading -> if (!loading) updateState { copy(isLoading = false) } },
                onError = { error -> updateState { copy(isLoading = false, errorType = error) } },
                onSuccess = { data -> updateState { copy(isLoading = false, popularMovies = data, errorType = null) } }
            )
        }
    }

    private fun searchMovies(query: String) {
        moviesJob?.cancel()
        moviesJob = viewModelScope.launch {
            updateState { copy(isLoading = true) }
            delay(1000.milliseconds)

            searchMoviesUseCase(query).collectAsResource(
                onLoading = { loading -> if (!loading) updateState { copy(isLoading = false) } },
                onError = { error -> updateState { copy(isLoading = false, errorType = error) } },
                onSuccess = { data -> updateState { copy(isLoading = false, popularMovies = data, errorType = null) } }
            )
        }
    }

    private fun loadMoviesByGenre(genreId: Int) {
        val genreName = currentState.genres.firstOrNull { it.id == genreId }?.name ?: return

        moviesJob?.cancel()
        moviesJob = viewModelScope.launch {
            updateState { copy(isLoading = true) }
            delay(1000.milliseconds)

            getMoviesByGenreUseCase(genreName).collectAsResource(
                onLoading = { loading -> if (!loading) updateState { copy(isLoading = false) } },
                onError = { error -> updateState { copy(isLoading = false, errorType = error) } },
                onSuccess = { data -> updateState { copy(isLoading = false, popularMovies = data, errorType = null) } }
            )
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase(Unit).collectAsResource(
                onLoading = { loading -> updateState { copy(isGenresLoading = loading) } },
                onError = { error -> updateState { copy(isLoading = false, errorType = error) } },
                onSuccess = { data ->
                    updateState { copy(genres = data) }
                    if (currentState.errorType != NetworkError.NO_INTERNET) {
                        loadPopularMovies()
                    }
                }
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
                            moviesJob?.cancel()
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
            loadPopularMovies()
        }
    }

    private fun handleMovieClick(movieId: Int, category: String) {
        emitSideEffect(HomeSideEffect.NavigateToDetail(movieId = movieId, category = category))
    }

    private fun handleToggleFavorite(movie: PopularMovie) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
        }
    }

    private fun handleRefresh() {
        updateState { copy(errorType = null, isLoading = true) }

        val selectedGenre = currentState.selectedGenreId
        val genresIsEmpty = currentState.genres.isEmpty()

        viewModelScope.launch {
            delay(2000.milliseconds)

            if (genresIsEmpty) {
                loadGenres()
            } else if (selectedGenre != null) {
                loadMoviesByGenre(selectedGenre)
            } else {
                loadPopularMovies()
            }
        }
    }

    private fun handleGenreSelected(genreId: Int) {
        val newGenreId = if (currentState.selectedGenreId == genreId) null else genreId

        updateState { copy(selectedGenreId = newGenreId) }

        if (currentState.errorType == NetworkError.NO_INTERNET) return

        if (newGenreId != null) {
            loadMoviesByGenre(newGenreId)
        } else {
            loadPopularMovies()
        }
    }
}