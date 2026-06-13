package com.example.movieapp.splash.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SplashViewModel: ViewModel(){

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    init {
        startSplash()
    }

    private fun startSplash(){
        viewModelScope.launch {
            delay(3000.milliseconds)
            _state.update { it.copy(isReadyToNavigate = true) }
        }
    }
}