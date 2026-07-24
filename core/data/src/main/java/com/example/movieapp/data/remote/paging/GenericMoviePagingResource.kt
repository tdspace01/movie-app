package com.example.movieapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.data.remote.model.movie.PopularMovieResponseDto
import com.example.movieapp.domain.model.movie.PopularMovie
import retrofit2.Response

open class GenericMoviePagingResource<T>(
    private val fetchPage:suspend (page:Int) -> Response<T>,
    private val results:(T) -> List<PopularMovieResponseDto.PopularMovieDto>,
    private val totalPages:(T) -> Int
): PagingSource<Int, PopularMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMovie> {
        val page = params.key ?: 1
        return try {
            val response = fetchPage(page)
            val body = response.body() ?:
                return LoadResult.Error(Exception("Empty response"))
            LoadResult.Page(
                data = results(body).map { it.toDomain() },
                prevKey = if(page == 1) null else page -1,
                nextKey = if(page >= totalPages(body)) null else page +1,
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PopularMovie>): Int? {
        return  state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}