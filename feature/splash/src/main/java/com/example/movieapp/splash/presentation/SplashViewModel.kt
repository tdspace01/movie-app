package com.example.movieapp.splash.presentation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.movieapp.ui.base.BaseViewModel
import kotlin.time.Duration.Companion.milliseconds

class SplashViewModel :
    BaseViewModel<SplashState, SplashEvent, SplashSideEffect>(SplashState()) {

    init {
        onEvent(SplashEvent.StartTimer)
    }

    override fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.StartTimer -> {
                viewModelScope.launch {
                    delay(2000.milliseconds)
                    emitSideEffect(SplashSideEffect.NavigateToHome)
                }
            }
        }
    }
}