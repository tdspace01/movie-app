<<<<<<< HEAD:feature/favourite/src/main/java/com/example/movieapp/favourite/favourite_screen/FavouriteScreen.kt
package com.example.movieapp.favourite
=======
package com.example.movieapp.favourite.favouritescreen
>>>>>>> fc8400e (Networking Branch: Api calls added):feature/favourite/src/main/java/com/example/movieapp/favourite/favouritescreen/FavouriteScreen.kt

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FavouriteScreen(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text("Im favourite")
    }
}