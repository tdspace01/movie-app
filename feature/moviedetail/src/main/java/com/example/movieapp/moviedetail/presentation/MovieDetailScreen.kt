package com.example.movieapp.moviedetail.presentation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieapp.designsystem.components.MovieAppAsyncImage
import com.example.movieapp.designsystem.components.MovieAppLoader
import com.example.movieapp.designsystem.components.MovieAppNetworkConnectionScreen
import com.example.movieapp.designsystem.components.MovieAppText
import com.example.movieapp.designsystem.design.MovieAppFontSize
import com.example.movieapp.designsystem.design.MovieAppLineHeight
import com.example.movieapp.designsystem.design.MovieAppShapes
import com.example.movieapp.designsystem.design.MovieAppSizing
import com.example.movieapp.designsystem.design.MovieAppSpacing
import com.example.movieapp.designsystem.theme.DarkColorScheme
import com.example.movieapp.moviedetail.R

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

@SuppressLint("FrequentlyChangingValue")
@Composable
private fun MovieDetailContent(
    state: MovieDetailState,
    category: String,
    onEvent: (MovieDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var isHeaderVisible by remember { mutableStateOf(true) }

    LaunchedEffect(scrollState.value) {
        isHeaderVisible = scrollState.value == 0
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            state.isRefreshing || state.isLoading -> {
                MovieAppLoader()
            }

            state.errorType != null -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    MovieAppNetworkConnectionScreen(
                        onRefresh = { onEvent(MovieDetailEvent.OnRefresh) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            state.movieDetail != null -> {
                val movie = state.movieDetail

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    Spacer(
                        modifier = Modifier
                            .statusBarsPadding()
                            .height(MovieAppSizing.size53)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(375f / 490f)
                    ) {
                        MovieAppAsyncImage(
                            imageUrl = movie.posterUrl ?: movie.backdropUrl,
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
                                    // movie trl
                                }
                                .padding(
                                    horizontal = MovieAppSizing.size24,
                                    vertical = MovieAppSizing.size12
                                )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(MovieAppSizing.size10)
                            ) {
                                MovieAppText(
                                    text = stringResource(R.string.trailer),
                                    fontSize = MovieAppFontSize.font13,
                                    fontWeight = FontWeight.Medium,
                                    color = DarkColorScheme.black
                                )
                                Image(
                                    painter = painterResource(R.drawable.trailer_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(MovieAppSizing.size10)
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
                                lineHeight = MovieAppLineHeight.line26,
                                modifier = Modifier.weight(1f)
                            )
                            Image(
                                painter = painterResource(
                                    if (movie.isFavorite) R.drawable.big_marked_heart
                                    else R.drawable.big_unmarked_heart
                                ),
                                contentDescription = null,
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
                                text = movie.rating.takeIf { it > 0 }?.let {
                                    "%.1f".format(it)
                                },
                                iconRes = R.drawable.start_icon
                            )
                            InfoChip(text = category.takeIf {
                                it != stringResource(R.string.na)
                            })
                            InfoChip(
                                text = movie.durationFormatted.takeIf { it.isNotBlank() },
                                iconRes = R.drawable.clock_icon
                            )
                            InfoChip(text = movie.releaseYear.takeIf { it.isNotBlank() })
                        }

                        Spacer(Modifier.height(MovieAppSpacing.spacing16))

                        MovieAppText(
                            text = stringResource(R.string.about_movie),
                            fontSize = MovieAppFontSize.font16,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkColorScheme.whisper,
                            lineHeight = MovieAppLineHeight.line21
                        )

                        Spacer(Modifier.height(MovieAppSpacing.spacing08))

                        MovieAppText(
                            text = movie.overview,
                            fontSize = MovieAppFontSize.font14,
                            fontWeight = FontWeight.Medium,
                            color = DarkColorScheme.lighterGrey,
                            lineHeight = MovieAppLineHeight.line18
                        )

                        Spacer(modifier = Modifier.height(MovieAppSizing.size20))
                    }
                }
            }
        }

        val shouldShowHeader =
            isHeaderVisible || state.isLoading || state.isRefreshing || state.errorType != null

        AnimatedVisibility(
            visible = shouldShowHeader,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
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
                    text = stringResource(R.string.details),
                    fontSize = MovieAppFontSize.font16,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkColorScheme.whisper,
                    textAlign = TextAlign.Center,
                    lineHeight = MovieAppLineHeight.line18,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
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
                    lineHeight = MovieAppLineHeight.line18,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            MovieAppText(
                text = text,
                fontSize = MovieAppFontSize.font14,
                fontWeight = FontWeight.Medium,
                color = DarkColorScheme.lightGrey,
                lineHeight = MovieAppLineHeight.line18,
                textAlign = TextAlign.Center
            )
        }
    }
}