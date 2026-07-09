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
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing

@Composable
fun MovieCardShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(163.5f / 226f)
                .clip(MovieAppShapes.corner16)
                .shimmerBrush()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MovieAppSpacing.spacing04)
                .height(MovieAppSizing.size42)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(MovieAppSizing.size18)
                    .clip(MovieAppShapes.corner50)
                    .shimmerBrush()
            )

            Spacer(modifier = Modifier.height(MovieAppSpacing.spacing04))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(MovieAppSizing.size16)
                    .clip(MovieAppShapes.corner50)
                    .shimmerBrush()
            )
        }
    }
}