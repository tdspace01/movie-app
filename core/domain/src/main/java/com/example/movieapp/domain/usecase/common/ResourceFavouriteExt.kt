package com.example.movieapp.domain.usecase.common

import androidx.paging.PagingData
import androidx.paging.map
import com.example.movieapp.common.resource.NetworkResource
import com.example.movieapp.common.resource.map
import com.example.movieapp.domain.model.movie.MovieDetail
import com.example.movieapp.domain.model.movie.PopularMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlin.jvm.JvmName

@JvmName("withFavouriteStatePaging")
fun Flow<PagingData<PopularMovie>>.withFavouriteState(
    favouriteIds: Flow<Set<Int>>,
): Flow<PagingData<PopularMovie>> = combine(this, favouriteIds) { pagingData, ids ->
    pagingData.map { movie -> movie.copy(isFavorite = movie.id in ids) }
}

@JvmName("withFavouriteStateDetail")
fun Flow<NetworkResource<MovieDetail>>.withFavouriteState(
    favouriteIds: Flow<Set<Int>>,
): Flow<NetworkResource<MovieDetail>> = combine(
    this,
    favouriteIds,
) { resource, ids ->
    resource.map { detail -> detail.copy(isFavorite = detail.id in ids) }
}