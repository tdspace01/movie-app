package com.example.movieapp.home.home

import androidx.lifecycle.viewModelScope
import com.example.movieapp.common.resource.collectAsResource
import com.example.movieapp.domain.usecase.movie.GetPopularMoviesUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.search.GetGenresUseCase
import com.example.movieapp.domain.usecase.search.GetMoviesByGenreUseCase
import com.example.movieapp.domain.usecase.search.SearchMoviesUseCase
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.FlowPreview
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
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(HomeState()) {

    init {
        onEvent(HomeEvent.LoadGenres)
        observeSearchQueryAndFetch()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadMovies -> loadPopularMovies()
            is HomeEvent.LoadGenres -> loadGenres()

            is HomeEvent.OnToggleFavorite -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(event.movie)
                }
            }

            is HomeEvent.OnClearSearch -> {
                updateState { copy(searchQuery = "") }
            }

            is HomeEvent.OnSearchQueryChanged -> {
                updateState { copy(searchQuery = event.query) }
            }

            is HomeEvent.OnRefresh -> {
                updateState { copy(errorType = null, isLoading = true) }
                val selectedGenre = currentState.selectedGenreId
                if (currentState.genres.isEmpty()) {
                    loadGenres()
                } else {
                    if (selectedGenre != null) loadMoviesByGenre(selectedGenre) else loadPopularMovies()
                }
            }

            is HomeEvent.OnFavoriteClick -> {
                emitSideEffect(HomeSideEffect.NavigateToFavorite)
            }

            is HomeEvent.OnToggleGenresVisibility -> {
                updateState { copy(isGenresVisible = !currentState.isGenresVisible) }
            }

            is HomeEvent.OnGenreSelected -> {
                val newGenreId = if (currentState.selectedGenreId == event.genreId) null else event.genreId
                updateState { copy(selectedGenreId = newGenreId) }
                if (newGenreId != null) loadMoviesByGenre(newGenreId) else loadPopularMovies()
            }

            is HomeEvent.OnGenreCleared -> {
                updateState { copy(selectedGenreId = null) }
                loadPopularMovies()
            }

            is HomeEvent.OnMovieClick -> {
                emitSideEffect(
                    HomeSideEffect.NavigateToDetail(
                        movieId = event.movieId,
                        category = event.category
                    )
                )
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQueryAndFetch() {
        viewModelScope.launch {
            state.map { it.searchQuery }
                .distinctUntilChanged()
                .drop(1)
                .debounce(400L.milliseconds)
                .collectLatest { query ->
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
        viewModelScope.launch {
            popularMoviesUseCase().collectAsResource(
                onLoading = { loading -> updateState { copy(isLoading = loading) } },
                onError = { error -> updateState { copy(errorType = error) } },
                onSuccess = { data -> updateState { copy(popularMovies = data, errorType = null) } }
            )
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            searchMoviesUseCase(query).collectAsResource(
                onLoading = { loading -> updateState { copy(isLoading = loading) } },
                onError = { error -> updateState { copy(errorType = error) } },
                onSuccess = { data -> updateState { copy(popularMovies = data, errorType = null) } }
            )
        }
    }

    private fun loadMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            getMoviesByGenreUseCase(genreId).collectAsResource(
                onLoading = { loading -> updateState { copy(isLoading = loading) } },
                onError = { error -> updateState { copy(errorType = error) } },
                onSuccess = { data -> updateState { copy(popularMovies = data, errorType = null) } }
            )
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase().collectAsResource(
                onLoading = { loading -> updateState { copy(isGenresLoading = loading) } },
                onError = { error -> updateState { copy(isLoading = false, errorType = error) } },
                onSuccess = { data ->
                    updateState { copy(genres = data) }
                    loadPopularMovies()
                }
            )
        }
    }
}