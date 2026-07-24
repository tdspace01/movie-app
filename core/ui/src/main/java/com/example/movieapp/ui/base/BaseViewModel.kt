package com.example.movieapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.common.networkstatus.NetworkStatus
import com.example.movieapp.ui.state.OfflineCapable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

abstract class BaseViewModel<State, Event, SideEffect>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    protected val currentState: State
        get() = _state.value

    open fun onEvent(event: Event) = Unit

    protected fun updateState(block: State.() -> State) {
        _state.update(block)
    }

    protected fun update(block: State.() -> State) = updateState(block)

    protected fun emitSideEffect(sideEffect: SideEffect) {
        viewModelScope.launch {
            _sideEffect.send(sideEffect)
        }
    }

    protected fun emit(effect: SideEffect) = emitSideEffect(effect)

    protected fun requireOnline(block: () -> Unit) {
        if ((currentState as? OfflineCapable)?.isOffline == true) return
        block()
    }

    protected fun observeNetwork(
        networkStatus: Flow<NetworkStatus>,
        onAvailable: State.() -> State,
        onUnavailable: State.() -> State,
    ) {
        viewModelScope.launch {
            networkStatus.collect { status ->
                update {
                    when (status) {
                        NetworkStatus.Available -> onAvailable()
                        NetworkStatus.Unavailable -> onUnavailable()
                    }
                }
            }
        }
    }

    protected fun refresh(
        isRefreshing: () -> Boolean,
        isConnected: suspend () -> Boolean,
        onStart: State.() -> State,
        onOffline: State.() -> State,
        onOnline: State.() -> State,
        onConnected: suspend () -> Unit,
    ) {
        if (isRefreshing()) return
        viewModelScope.launch {
            update(onStart)
            delay(2.seconds)
            val connected = isConnected()
            update { if (connected) onOnline() else onOffline() }
            if (connected) onConnected()
        }
    }
}