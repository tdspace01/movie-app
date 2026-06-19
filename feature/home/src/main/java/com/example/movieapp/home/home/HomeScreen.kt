<<<<<<< HEAD:feature/home/src/main/java/com/example/movieapp/home/home_screen/HomeScreen.kt
package com.example.movieapp.home
=======
package com.example.movieapp.home.home
>>>>>>> fc8400e (Networking Branch: Api calls added):feature/home/src/main/java/com/example/movieapp/home/home/HomeScreen.kt

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text("Im home")
    }
}