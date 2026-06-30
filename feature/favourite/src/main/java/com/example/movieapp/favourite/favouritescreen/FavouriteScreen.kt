package com.example.movieapp.favourite.favouritescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FavouriteScreen(
    viewModel: FavoriteViewModel,
    onNavigateToDetails: (Int, String) -> Unit,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text("Im favourite")
    }
}