<<<<<<< HEAD:feature/moviedetail/src/main/java/com/example/movieapp/moviedetail/movie_detail_screen/MovieDetailScreen.kt
package com.example.movieapp.moviedetail
=======
package com.example.movieapp.moviedetail.moviedetailscreen
>>>>>>> fc8400e (Networking Branch: Api calls added):feature/moviedetail/src/main/java/com/example/movieapp/moviedetail/moviedetailscreen/MovieDetailScreen.kt

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MovieDetailScreen(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text("Im movie detail screen")
    }
}