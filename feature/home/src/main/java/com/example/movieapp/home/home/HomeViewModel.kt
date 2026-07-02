package com.example.movieapp.home.home

import kotlin.collections.map
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import com.example.movieapp.ui.base.BaseViewModel
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.flow.distinctUntilChanged
import com.example.movieapp.domain.model.movie.PopularMovie
import com.example.movieapp.common.resource.collectAsResource
import com.example.movieapp.domain.usecase.search.GetGenresUseCase
import com.example.movieapp.domain.usecase.search.SearchMoviesUseCase
import com.example.movieapp.domain.usecase.movie.ToggleFavouriteUseCase
import com.example.movieapp.domain.usecase.movie.GetPopularMoviesUseCase
import com.example.movieapp.domain.usecase.search.GetMoviesByGenreUseCase
import com.example.movieapp.domain.repository.movie.FavouriteMovieRepository

class HomeViewModel(
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    private val toggleFavoriteUseCase: ToggleFavouriteUseCase,
    private val favouriteMovieRepository: FavouriteMovieRepository
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(HomeState()) {

    private var currentMovies: List<PopularMovie> = emptyList()

    init {
        onEvent(HomeEvent.LoadGenres)
        observeSearchQueryAndFetch()
        observeFavourites()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {

            is HomeEvent.LoadMovies -> loadPopularMovies()

            is HomeEvent.LoadGenres -> loadGenres()

            is HomeEvent.OnClearSearch -> {
                updateState { copy(searchQuery = "") }
            }

            is HomeEvent.OnSearchQueryChanged -> {
                updateState { copy(searchQuery = event.query) }
            }

            is HomeEvent.OnFavoriteClick -> {
                emitSideEffect(HomeSideEffect.NavigateToFavorite)
            }

            is HomeEvent.OnToggleGenresVisibility -> {
                updateState { copy(isGenresVisible = !currentState.isGenresVisible) }
            }

            is HomeEvent.OnGenreCleared -> {
                updateState { copy(selectedGenreId = null) }
                loadPopularMovies()
            }

            is HomeEvent.OnMovieClick -> {
                emitSideEffect( HomeSideEffect.NavigateToDetail( movieId = event.movieId, category = event.category) )
            }

            is HomeEvent.OnToggleFavorite -> {
                updateState {
                    copy(
                        popularMovies = popularMovies.map {
                            if (it.id == event.movie.id)
                                it.copy(isFavorite = !it.isFavorite)
                            else it
                        }
                    )
                }

                viewModelScope.launch {
                    toggleFavoriteUseCase(event.movie)
                }
            }

            is HomeEvent.OnRefresh -> {
                updateState { copy(errorType = null, isLoading = true) }

                val selectedGenre = currentState.selectedGenreId
                val genresIsEmpty = currentState.genres.isEmpty()

                viewModelScope.launch {
                    delay(2000.milliseconds)

                    if (genresIsEmpty) {
                        loadGenres()
                    } else {
                        if (selectedGenre != null) {
                            loadMoviesByGenre(selectedGenre)
                        } else {
                            loadPopularMovies()
                        }
                    }
                }
            }


            is HomeEvent.OnGenreSelected -> {
                val newGenreId =
                    if (currentState.selectedGenreId == event.genreId) null else event.genreId

                updateState { copy(selectedGenreId = newGenreId) }

                if (newGenreId != null)
                    loadMoviesByGenre(newGenreId)
                else
                    loadPopularMovies()
            }
        }
    }

    private fun observeFavourites() {
        viewModelScope.launch {
            favouriteMovieRepository.getFavouriteIds()
                .collect { ids ->
                    updateState {
                        copy(popularMovies = currentMovies.map { movie ->
                            movie.copy(isFavorite = movie.id in ids)
                        })
                    }
                }
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
                onSuccess = { data ->
                    currentMovies = data
                    updateState { copy(popularMovies = data,errorType = null) }
                }
            )
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            searchMoviesUseCase(query).collectAsResource(
                onLoading = { loading -> updateState { copy(isLoading = loading) } },
                onError = { error -> updateState { copy(errorType = error) } },
                onSuccess = { data ->
                    currentMovies = data
                    updateState { copy(popularMovies = data,errorType = null) }
                }
            )
        }
    }

    private fun loadMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            getMoviesByGenreUseCase(genreId).collectAsResource(
                onLoading = { loading -> updateState { copy(isLoading = loading) } },
                onError = { error -> updateState { copy(errorType = error) } },
                onSuccess = { data ->
                    currentMovies = data
                    updateState { copy(popularMovies = data,errorType = null) }
                }
            )
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase(Unit).collectAsResource(
                onLoading = { loading ->updateState { copy(isGenresLoading = loading) } },
                onError = { error -> updateState { copy(isLoading = false, errorType = error) } },
                onSuccess = { data -> updateState { copy(genres = data) } ;loadPopularMovies() }
            )
        }
    }
}