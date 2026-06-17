package com.example.movieapp.network.response_handler

import com.example.movieapp.common.resource.Resource
import com.example.movieapp.network.api_response.ApiResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ResponseHandler(
    private val json: Json
) {
    fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Flow<Resource<T>> = flow {
        emit(Resource.Loading(isLoading = true))
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Resource.Success(data = body))
                } else {
                    emit(Resource.Error("The response body was empty."))
                }
            } else {
                val errorJson = response.errorBody()?.string()
                val errorMessage = errorJson?.let {
                    try {
                        json.decodeFromString<ApiResponseDto<Unit>>(it).message
                    } catch (_: Exception) { null }
                } ?: "Server error: ${response.code()}"

                emit(Resource.Error(message = errorMessage))
            }
        } catch (_: IOException) {
            emit(Resource.Error("No internet connection available. Please check your network."))
        } catch (_: HttpException) {
            emit(Resource.Error("Server is currently unreachable. Please try again later."))
        } catch (e: Exception) {
            val message = e.localizedMessage ?: "An unexpected network error occurred."
            emit(Resource.Error(message))
        } finally {
            emit(Resource.Loading(isLoading = false))
        }
    }
}