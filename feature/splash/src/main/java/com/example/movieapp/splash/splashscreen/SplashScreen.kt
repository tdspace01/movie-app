<<<<<<< HEAD:feature/splash/src/main/java/com/example/movieapp/splash/splash_screen/SplashScreen.kt
=======
package com.example.movieapp.splash.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    onNavigateToHome:()-> Unit,
    viewModel: SplashViewModel
){
    LaunchedEffect(Unit) {
        viewModel.state.collectLatest { state->
            if(state.isReadyToNavigate){
                onNavigateToHome()
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(com.example.movieapp.splash.R.drawable.splash_logo),
            contentDescription = null,
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
        )
    }
}
>>>>>>> fc8400e (Networking Branch: Api calls added):feature/splash/src/main/java/com/example/movieapp/splash/splashscreen/SplashScreen.kt
