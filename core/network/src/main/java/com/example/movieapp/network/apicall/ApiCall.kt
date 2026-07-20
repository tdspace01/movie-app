package com.example.movieapp.network.apicall

import com.example.movieapp.common.resource.NetworkError
import com.example.movieapp.common.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun <T> apiCall(
    apiCall: suspend () -> Response<T>
): Flow<Resource<T>> = flow {

    emit(Resource.Loading(isLoading = true))

    val result = runCatching { apiCall() }.fold(
        onSuccess = { response->
          if(response.isSuccessful){
              val body = response.body()
              if(body != null){
                  Resource.Success(body)
              }else{
                  Resource.Error(NetworkError.EMPTY_RESPONSE)
              }
          }else{
                val errorBody = response.errorBody()?.string()
                val errorType = when(response.code()) {
                    401 -> NetworkError.UNAUTHORIZED
                    404 -> NetworkError.NOT_FOUND
                    else -> NetworkError.UNKNOWN
                }
              Resource.Error(errorType, message = errorBody)
          }
        },

        onFailure = { e->
            val errorType = when(e){
                is IOException -> NetworkError.NO_INTERNET
                is HttpException -> NetworkError.SERVER_UNREACHABLE
                else -> NetworkError.UNKNOWN
            }
            Resource.Error(errorType, message = e.localizedMessage)
        }
    )

    emit(result)
    emit(Resource.Loading(isLoading = false))
}