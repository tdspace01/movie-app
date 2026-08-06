package com.example.movieapp.moviedetail.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.designsystem.components.MovieAppAsyncImage
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppLineHeight
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.moviedetail.R
import com.example.movieapp.moviedetail.presentation.contract.MovieDetailEvent

@Composable
internal fun MovieDetailBody(
    movie: MovieDetail,
    category: String,
    onEvent: (MovieDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        MovieDetailPoster(
            movie = movie,
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MovieAppSpacing.spacing16),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                MovieAppText(
                    text = movie.title,
                    fontSize = MovieAppFontSize.font20,
                    fontWeight = FontWeight.Bold,
                    color = DarkColorScheme.whisper,
                    lineHeight = MovieAppLineHeight.line26,
                    modifier = Modifier.weight(1f),
                )
                Image(
                    painter = painterResource(
                        if (movie.isFavorite) R.drawable.big_marked_heart
                        else R.drawable.big_unmarked_heart
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(MovieAppSizing.size24)
                        .clickable { onEvent(MovieDetailEvent.OnToggleFavorite) },
                )
            }

            Spacer(Modifier.height(MovieAppSpacing.spacing10))

            Row(
                horizontalArrangement = Arrangement.spacedBy(MovieAppSpacing.spacing08),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MovieDetailInfoChip(
                    text = movie.rating,
                    iconRes = R.drawable.start_icon,
                )
                MovieDetailInfoChip(
                    text = category.takeIf { it != stringResource(R.string.na) },
                )
                MovieDetailInfoChip(
                    text = movie.durationFormatted.takeIf { it.isNotBlank() },
                    iconRes = R.drawable.clock_icon,
                )
                MovieDetailInfoChip(
                    text = movie.releaseYear.takeIf { it.isNotBlank() },
                )
            }

            Spacer(Modifier.height(MovieAppSpacing.spacing16))

            MovieAppText(
                text = stringResource(R.string.about_movie),
                fontSize = MovieAppFontSize.font16,
                fontWeight = FontWeight.SemiBold,
                color = DarkColorScheme.whisper,
                lineHeight = MovieAppLineHeight.line21,
            )

            Spacer(Modifier.height(MovieAppSpacing.spacing08))

            MovieAppText(
                text = movie.overview,
                fontSize = MovieAppFontSize.font14,
                fontWeight = FontWeight.Medium,
                color = DarkColorScheme.lighterGrey,
                lineHeight = MovieAppLineHeight.line18,
            )

            Spacer(modifier = Modifier.height(MovieAppSizing.size36))
        }
    }
}

@Composable
private fun MovieDetailPoster(
    movie: MovieDetail,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.aspectRatio(375f / 490f),
    ) {
        MovieAppAsyncImage(
            imageUrl = movie.backdropUrl ?: movie.posterUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f),
                        ),
                    ),
                ),
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = MovieAppSpacing.spacing16,
                    bottom = MovieAppSpacing.spacing30,
                )
                .clip(MovieAppShapes.corner16)
                .background(DarkColorScheme.primaryYellow)
                .clickable { /* trailer */ }
                .padding(
                    horizontal = MovieAppSizing.size24,
                    vertical = MovieAppSizing.size12,
                ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size10),
            ) {
                MovieAppText(
                    text = stringResource(R.string.trailer),
                    fontSize = MovieAppFontSize.font13,
                    fontWeight = FontWeight.Medium,
                    color = DarkColorScheme.black,
                )
                Image(
                    painter = painterResource(R.drawable.trailer_icon),
                    contentDescription = null,
                    modifier = Modifier.size(MovieAppSizing.size10),
                )
            }
        }
    }
}

@Preview
@Composable
private fun MovieDetailBodyP() {
    MovieDetailBody(
        movie = previewMovieDetail,
        category = "Sci-Fi",
        onEvent = {},
    )
}

@Preview
@Composable
private fun MovieDetailBodyNoBackdropP() {
    MovieDetailBody(
        movie = previewMovieDetail.copy(
            backdropUrl = null,
            isFavorite = false,
        ),
        category = "Sci-Fi",
        onEvent = {},
    )
}

@Preview
@Composable
private fun MovieDetailBodyMissingMetadataP() {
    MovieDetailBody(
        movie = previewMovieDetail.copy(
            durationFormatted = "",
            releaseYear = "",
        ),
        category = "N/A",
        onEvent = {},
    )
}

private val previewMovieDetail = MovieDetail(
    id = 1,
    title = "Interstellar",
    posterUrl = "https://image.tmdb.org/t/p/w500/sample_poster.jpg",
    backdropUrl = "https://image.tmdb.org/t/p/w780/sample_backdrop.jpg",
    overview = "A team of explorers travel through a wormhole in space in an attempt to ensure " +
            "humanity's survival by finding a new habitable planet beyond our solar system.",
    rating = "8.6",
    durationFormatted = "2h 49m",
    releaseYear = "2014",
    isFavorite = true,
)