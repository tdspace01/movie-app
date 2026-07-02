package com.example.movieapp.moviedetail.moviedetailscreen

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.movieapp.designsystem.R
import com.example.movieapp.designsystem.components.MovieAppLoader
import com.example.movieapp.designsystem.components.MovieAppNetworkConnectionScreen
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    category: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is MovieDetailSideEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    MovieDetailContent(
        state = state,
        category = category,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
private fun MovieDetailContent(
    state: MovieDetailState,
    category: String,
    onEvent: (MovieDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                MovieAppLoader()
            }

            state.errorType != null -> {
                MovieAppNetworkConnectionScreen(
                    onRefresh = { onEvent(MovieDetailEvent.OnRefresh) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            state.movieDetail != null -> {
                val movie = state.movieDetail

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(
                        modifier = Modifier
                            .statusBarsPadding()
                            .height(56.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(375f / 490f)
                    ) {
                        AsyncImage(
                            model = movie.posterUrl ?: movie.backdropUrl,
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.3f),
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(
                                    end = MovieAppSpacing.spacing16,
                                    bottom = MovieAppSpacing.spacing30
                                )
                                .clip(MovieAppShapes.corner16)
                                .background(DarkColorScheme.primaryYellow)
                                .clickable {
                                    //movie trl
                                }
                                .padding(horizontal = 24.dp, vertical = 12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                MovieAppText(
                                    text = "Trailer",
                                    fontSize = MovieAppFontSize.font13,
                                    fontWeight = FontWeight.Medium,
                                    color = DarkColorScheme.black
                                )
                                Image(
                                    painter = painterResource(R.drawable.trailer_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(10.dp)
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MovieAppSpacing.spacing16)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            MovieAppText(
                                text = movie.title,
                                fontSize = MovieAppFontSize.font20,
                                fontWeight = FontWeight.Bold,
                                color = DarkColorScheme.whisper,
                                lineHeight = 26.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Image(
                                painter = painterResource(
                                    if (movie.isFavorite) R.drawable.big_marked_heart else R.drawable.big_unmarked_heart
                                ),
                                contentDescription = "Favorite",
                                modifier = Modifier
                                    .size(MovieAppSizing.size24)
                                    .clickable { onEvent(MovieDetailEvent.OnToggleFavorite) }
                            )
                        }

                        Spacer(Modifier.height(MovieAppSpacing.spacing10))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MovieAppSpacing.spacing08),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            InfoChip(
                                text = movie.rating.takeIf { it > 0 }?.let { "%.1f".format(it) },
                                iconRes = R.drawable.start_icon
                            )
                            InfoChip(text = category.takeIf { it != "N/A" })
                            InfoChip(
                                text = movie.durationFormatted.takeIf { it.isNotBlank() },
                                iconRes = R.drawable.clock_icon
                            )
                            InfoChip(text = movie.releaseYear.takeIf { it.isNotBlank() })
                        }

                        Spacer(Modifier.height(MovieAppSpacing.spacing16))

                        MovieAppText(
                            text = "About movie",
                            fontSize = MovieAppFontSize.font16,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkColorScheme.whisper,
                            lineHeight = 21.sp
                        )

                        Spacer(Modifier.height(MovieAppSpacing.spacing08))

                        MovieAppText(
                            text = movie.overview,
                            fontSize = MovieAppFontSize.font14,
                            fontWeight = FontWeight.Medium,
                            color = DarkColorScheme.lighterGrey,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(
                    horizontal = MovieAppSpacing.spacing16,
                    vertical = MovieAppSpacing.spacing12
                )
        ) {
            Image(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = null,
                modifier = Modifier
                    .width(MovieAppSizing.size10)
                    .height(MovieAppSizing.size18)
                    .align(Alignment.CenterStart)
                    .clickable { onEvent(MovieDetailEvent.OnBackClick) }
            )
            MovieAppText(
                text = "Details",
                fontSize = MovieAppFontSize.font16,
                fontWeight = FontWeight.SemiBold,
                color = DarkColorScheme.whisper,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun InfoChip(
    text: String?,
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int? = null,
) {
    if (text.isNullOrBlank()) return

    Box(
        modifier = modifier
            .clip(MovieAppShapes.corner50)
            .background(DarkColorScheme.darkestGrey)
            .padding(horizontal = MovieAppSpacing.spacing10, vertical = MovieAppSpacing.spacing04),
        contentAlignment = Alignment.Center
    ) {
        if (iconRes != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(MovieAppSizing.size16)
                )
                MovieAppText(
                    text = text,
                    fontSize = MovieAppFontSize.font14,
                    fontWeight = FontWeight.Medium,
                    color = DarkColorScheme.lightGrey,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            MovieAppText(
                text = text,
                fontSize = MovieAppFontSize.font14,
                fontWeight = FontWeight.Medium,
                color = DarkColorScheme.lightGrey,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}