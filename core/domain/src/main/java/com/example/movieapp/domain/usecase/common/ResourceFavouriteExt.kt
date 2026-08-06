package com.example.movieapp.domain.usecase.common

import androidx.paging.PagingData
import androidx.paging.map
import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.common.resource.map
import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.domain.model.movie.PopularMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
fun Flow<PagingData<PopularMovie>>.withPagingFavouriteState(
    favouriteIds: Flow<Set<Int>>,
): Flow<PagingData<PopularMovie>> = favouriteIds.flatMapLatest { ids ->
    map { pagingData -> pagingData.map { movie -> movie.copy(isFavorite = movie.id in ids) } }
}

fun Flow<NetworkResource<MovieDetail>>.withDetailFavouriteState(
    favouriteIds: Flow<Set<Int>>,
): Flow<NetworkResource<MovieDetail>> = combine(
    this,
    favouriteIds,
) { resource, ids ->
    resource.map { detail -> detail.copy(isFavorite = detail.id in ids) }
}