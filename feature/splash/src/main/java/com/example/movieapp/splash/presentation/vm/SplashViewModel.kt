package com.example.movieapp.splash.presentation.vm

import androidx.lifecycle.viewModelScope
import com.example.movieapp.splash.presentation.contract.SplashSideEffect
import com.example.movieapp.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SplashViewModel :
    BaseViewModel<Unit, Unit, SplashSideEffect>(Unit) {

    init {
        viewModelScope.launch {
            delay(1000.milliseconds)
            emit(SplashSideEffect.NavigateToHome)
        }
    }
}