package com.example.movieapp.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing

@Composable
fun MovieCardShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(MovieAppShapes.corner16)
            .shimmerBrush()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MovieAppSizing.size12)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(16.dp)
                    .clip(MovieAppShapes.corner50)
                    .shimmerBrush()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(MovieAppSizing.size12)
                    .clip(MovieAppShapes.corner50)
                    .shimmerBrush()
            )
        }
    }
}