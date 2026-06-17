package com.example.movieapp.network.auth_interceptor

import com.example.movieapp.domain.usecase.AuthTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authTokenUseCase: AuthTokenUseCase
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking {
            authTokenUseCase.get().first()
        }

        val requestBuilder = originalRequest.newBuilder()
        if(!token.isNullOrBlank()) {
            requestBuilder.addHeader("Authorization","Barrer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}